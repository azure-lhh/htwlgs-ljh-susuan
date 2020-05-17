package cn.htwlgs.guanwang.service;

import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.PumpingStation;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.PumpingStationRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import cn.htwlgs.common.utils.CopyUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
public class PumpingStationService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;

    @Autowired
    private PumpingStationRepository repository;

    public PumpingStationDto findById(String id) {
        Optional<PumpingStation> opt = repository.findById(Integer.valueOf(id));
        if(!opt.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new PumpingStationDto(opt.get());
    }

    public PageList<PumpingStationDto> getPageList(String name, Integer pageNum, Integer pageSize) {
        Page<PumpingStation> page = getPageByParam(name,pageNum,pageSize);
        List<PumpingStation> stations = page.getContent();
        if(CollectionUtils.isEmpty(stations)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<PumpingStationDto> list = copyList(stations);
        return new PageList(list,pageNum,pageSize,page.getTotalElements());
    }

    public Page<PumpingStation> getPageByParam(String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByStationNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(PumpingStationDto dto) {

        repository.save(new PumpingStation(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modify(PumpingStationDto dto) {
        Optional<PumpingStation> data = repository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        repository.saveAndFlush(data.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> idList) {
        List<Integer> ids = new ArrayList<>();
        idList.forEach(item->{
            ids.add(Integer.parseInt(item));
        });
        repository.deleteByIdIn(ids);
    }

    public void importExcel(MultipartFile file) throws IOException {
        AnalysisEventListener<PumpingStationDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),50);
        EasyExcel.read(file.getInputStream(), PumpingStationDto.class, userAnalysisEventListener).sheet().doRead();
    }

    public Consumer<List<PumpingStationDto>> batchInsert(){
        return list -> savePumpingStation(list);
    }

    private void savePumpingStation(List<PumpingStationDto> list) {
        List<PumpingStation> stations = new ArrayList<>();
        list.forEach(item->{
            stations.add(new PumpingStation(item));
        });
        repository.saveAll(stations);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String station, Class<PumpingStationDto> pumpingStationDtoClass, ExcelTypeEnum xlsx,List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException(Constants.EXPORT_DATA_NULL_ERROR);
        }
        List<PumpingStationDto> list = findByIdList(idList);
        EasyExcelUtil.exportExcel(response, list,fileName , station, pumpingStationDtoClass,xlsx);
    }

    public List<PumpingStationDto> findByIdList(List<Integer> idList){
        List<PumpingStation> stations = repository.findByIdIn(idList);
        List<PumpingStationDto> list = copyList(stations);
        return list;
    }

    public void downloadExcel(HttpServletResponse response, String fileName, String station, Class<PumpingStationDto> pumpingStationDtoClass, ExcelTypeEnum xlsx) throws IOException {
        List<PumpingStationDto> list = new LinkedList<>();
        PumpingStationDto dto = JSON.parseObject("{\"id\":\"1\",\"station_name\":\"长寿湖泵站\",\"area_code\":\"csh-klj1\",\"address\":\"长寿区长寿湖\",\"wl\":\"0\",\"traffic\":\"0\",\"voltage\":\"0\",\"current\":\"0\",\"power\":\"0\",\"signal\":\"开\",\"situation\":\"无故障\",\"lon\":\"0\",\"lat\":\"0\",\"create_time\":\"2020-04-21 02:51:01\",\"create_user\":\"张大海\",\"setup_time\":\"2020-04-22 04:54:18\"}",PumpingStationDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list, fileName, station, pumpingStationDtoClass, xlsx);
    }

    private List<PumpingStationDto> copyList(List<PumpingStation> stations){
        List<PumpingStationDto> list = new ArrayList<>();
        stations.forEach(item->{
            list.add(new PumpingStationDto(item));
        });
        return list;
    }

    public PumpingStationStatisticsDto getStatisticsDto() {
        Map<String, Object> map = repository.getpumpingStatisticsDto();
        PumpingStatisticsDto statistics = JSONArray.parseObject(JSON.toJSONString(map),PumpingStatisticsDto.class);
        PumpingStationStatisticsDto dto = new PumpingStationStatisticsDto();
        List<StatisticsVarCherDto> signalList = new ArrayList<>();
        List<StatisticsVarCherDto> wlList = new ArrayList<>();
        List<StatisticsVarCherDto> trafficList = new ArrayList<>();
        signalList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_SIGNAL_OFF,statistics.getSignalOff()));
        signalList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_SIGNAL_ON,statistics.getSignalOn()));
        wlList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_WL_MAX,statistics.getWlMax()));
        wlList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_WL_MIN,statistics.getWlMin()));
        trafficList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_TRAFFIC_MIN,statistics.getTrafficMin()));
        trafficList.add(new StatisticsVarCherDto(Constants.PUMPING_STATION_STATISTICS_TRAFFIC_MAX,statistics.getTrafficMax()));
        dto.setWlList(wlList);
        dto.setSignalList(signalList);
        dto.setTrafficList(trafficList);
        return dto;
    }

    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
    }

    public List<StatisticsVarCherDto> getGroupByState() {
        List<Map<String, Object>> maps = repository.getGroupByState();
        List<StatisticsVarCherDto> list = JSONArray.parseArray(JSON.toJSONString(maps), StatisticsVarCherDto.class);
        return dataService.groupByState(list);
    }
}
