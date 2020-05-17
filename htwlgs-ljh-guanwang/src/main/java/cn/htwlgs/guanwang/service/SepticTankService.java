package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.SepticTank;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.SepticTankRepository;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service
public class SepticTankService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;

    @Autowired
    private SepticTankRepository repository;

    public SepticTankDto findById(String id) {
        Optional<SepticTank> tank = repository.findById(Integer.valueOf(id));
        if(!tank.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new SepticTankDto(tank.get());
    }

    public Page<SepticTank> getPageByParam(String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByPoolNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }

    public PageList<SepticTankDto> getPageList(String name, Integer pageNum, Integer pageSize) {
        Page<SepticTank> page = getPageByParam(name,pageNum,pageSize);
        List<SepticTank> tanks = page.getContent();
        if(CollectionUtils.isEmpty(tanks)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<SepticTankDto> list = copyList(tanks);
        return new PageList<>(list,pageNum,pageSize,page.getTotalElements());
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(SepticTankDto dto) {
        repository.save(new SepticTank(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modify(SepticTankDto dto) {
        Optional<SepticTank> data = repository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        repository.saveAndFlush(data.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void remove(List<String> idList) {
        /*List<Integer> ids =Arrays.stream(idList.toArray(new String[idList.size()])).
                map(s->Integer.parseInt(s.trim())).collect(Collectors.toList());*/
        List<Integer> ids = new ArrayList<>();
        idList.forEach(item->{
            ids.add(Integer.parseInt(item));
        });
        repository.deleteByIdIn(ids);
    }

    public void importExcel(MultipartFile file) throws IOException {
        AnalysisEventListener<SepticTankDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),50);
        EasyExcel.read(file.getInputStream(), SepticTankDto.class, userAnalysisEventListener).sheet().doRead();
    }

    public Consumer<List<SepticTankDto>> batchInsert(){
        return list -> saveSepticTank(list);
    }

    private void saveSepticTank(List<SepticTankDto> list) {
        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(Constants.DATA_NULL_ERROR);
        }
        List<SepticTank> parts = new ArrayList<>();
        list.forEach(dto->{
            parts.add(new SepticTank(dto));
        });
        repository.saveAll(parts);
    }


    public void exportExcel(HttpServletResponse response, String fileName, String tank, Class<SepticTankDto> septicTankDtoClass, ExcelTypeEnum xlsx, List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException(Constants.EXPORT_DATA_NULL_ERROR);
        }
        List<SepticTankDto> list = findByIdList(idList);
        EasyExcelUtil.exportExcel(response, list,fileName , tank, septicTankDtoClass,xlsx);
    }

    public List<SepticTankDto> findByIdList(List<Integer> idList){
        List<SepticTank> tanks = repository.findByIdIn(idList);
        List<SepticTankDto> list = copyList(tanks);
        return list;
    }

    public void downloadExcel(HttpServletResponse response, String fileName, String tank, Class<SepticTankDto> septicTankDtoClass, ExcelTypeEnum xlsx) throws IOException {
        List<SepticTankDto> list = new LinkedList<>();
        SepticTankDto dto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"area_code\",\"500118001003\",\"id\":\"1\",\"pool_name\":\"m07号化粪池\",\"address\":\"双桥区天涯路116号\",\"pool_code\":\"asjdfkd\",\"mark\":\"0\",\"scale\":\"0\",\"handle\":\"0\",\"state\":\"运行\",\"situation\":\"0\",\"lon\":\"1.33\",\"lat\":\"1.44\",\"create_user\":\"王大伟\"}",SepticTankDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list,fileName , tank, septicTankDtoClass,xlsx);
    }

    private List<SepticTankDto> copyList(List<SepticTank> tanks){
        List<SepticTankDto> list = new ArrayList<>();
        tanks.forEach(item->{
            list.add(new SepticTankDto(item));
        });
        return list;
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
        //return septicTankDao.findAllId();
    }

    public SepticTankStatisticsDto getStatisticsDto() {
        Map<String, Object> map = repository.getStatisticsDto();
        SepticTankStatisticsEntityDto statistics = JSONArray.parseObject(JSON.toJSONString(map),SepticTankStatisticsEntityDto.class);
        SepticTankStatisticsDto dto = new SepticTankStatisticsDto();
        List<StatisticsVarCherDto> markList = new ArrayList<>();
        List<StatisticsVarCherDto> handleList = new ArrayList<>();
        List<StatisticsVarCherDto> stateList = new ArrayList<>();
        markList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_MARK_MAX,statistics.getMarkMax()));
        markList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_MARK_MIN,statistics.getMarkMin()));
        handleList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_HANDLE_MAX,statistics.getHandleMax()));
        handleList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_HANDLE_MIN,statistics.getHandleMin()));
        stateList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_STATE_OFF,statistics.getStateOff()));
        stateList.add(new StatisticsVarCherDto(Constants.SEPTIC_TANK_STATISTICS_STATE_ON,statistics.getStateOn()));
        dto.setMarkList(markList);
        dto.setHandleList(handleList);
        dto.setStateList(stateList);
        return dto;
    }

    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }

    public List<StatisticsVarCherDto> getGroupByState() {
        List<Map<String, Object>> maps = repository.getGroupByState();
        List<StatisticsVarCherDto> list = JSONArray.parseArray(JSON.toJSONString(maps), StatisticsVarCherDto.class);
        return dataService.groupByState(list);
    }
}
