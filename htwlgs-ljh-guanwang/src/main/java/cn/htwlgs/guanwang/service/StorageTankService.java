package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.StorageTank;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.StorageTankRepository;
import cn.htwlgs.guanwang.utils.*;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
@Service
public class StorageTankService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;

    @Autowired
    StorageTankRepository repository;


    public PageList<StorageTankListDto> getPageList(String keyWord, Integer pageNum, Integer pageSize) {

        Page<StorageTank> page = getPageByParam(keyWord,pageNum,pageSize);
        List<StorageTank> stations = page.getContent();
        if(CollectionUtils.isEmpty(stations)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<StorageTankListDto> list = new ArrayList<>();
        stations.forEach(item -> {
            StorageTankListDto dto = new StorageTankListDto();
            //把item 中 与dto 属性名称相同且值不为空的 复制给dto
            CopyUtils.copyProperties(item, dto);
            list.add(dto);
        });
        return new PageList(list, pageNum, pageSize, page.getTotalElements());
    }

    public Page<StorageTank> getPageByParam(String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByTankNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }


    public StorageTankListDto findStorageTankById(String id) {
        Optional<StorageTank> tank = repository.findById(Integer.valueOf(id));
        if(!tank.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        StorageTank storageTank = tank.get();
        StorageTankListDto storageTankListDto = new StorageTankListDto();
        CopyUtils.copyProperties(storageTank,storageTankListDto);
        return storageTankListDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(StorageTankDto storageTankDto) {
        Optional<StorageTank> byId = repository.findByTankCode(storageTankDto.getTankCode());
        if(byId.isPresent()){
            throw new BusinessException(Constants.SERIAL_NO_EXIST_ERROR);
        }
        StorageTank storageTank = new StorageTank();
        CopyUtils.copyProperties(storageTankDto, storageTank);
        storageTank.setCreateTime(LocalDateTime.now());
        storageTank.setCreateUser(UserUtils.getUserName());
        storageTank.setDeleted(Long.valueOf(0));
        repository.save(storageTank);

    }

    public void modify(StorageTankDto dto) {
        Optional<StorageTank> byId = repository.findById(dto.getId());
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
//        StorageTank storageTank = new StorageTank();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setUpdateUser(UserUtils.getUserName());
        byId.get().setDeleted(Long.valueOf(0));
        byId.get().setUpdateTime(LocalDateTime.now());
        repository.saveAndFlush(byId.get());
    }

    public int remove(List<Integer> stringList) {
        return repository.deleteAllByIdIn(stringList);
    }


    public void importExcel( MultipartFile file) throws IOException {
        AnalysisEventListener<StorageTankDto > userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),2);
        EasyExcel.read(file.getInputStream(), StorageTankDto.class, userAnalysisEventListener).sheet().doRead();
    }


    public Consumer<List<StorageTankDto>> batchInsert(){
        return list -> saveStorageTank(list);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String station, Class<StorageTankDto> interceptingWellDtoClass, ExcelTypeEnum xlsx, List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<StorageTankDto> list = findByIdList(idList);
        EasyExcelUtil.exportExcel(response, list,fileName , station, interceptingWellDtoClass,xlsx);
    }

    public void saveStorageTank(List<StorageTankDto>  list){
        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException("数据为空");
        }
        List<StorageTank> parts = new ArrayList<>();
        list.forEach(dto->{
            StorageTank storageTank = new StorageTank();
            CopyUtils.copyProperties(dto, storageTank);
            storageTank.setCreateTime(LocalDateTime.now());
            storageTank.setCreateUser(UserUtils.getUserName()); // 创建人暂时无法获取
            storageTank.setDeleted(Long.valueOf(0));
            parts.add(storageTank);

        });
        repository.saveAll(parts);


    }

    public List<StorageTankDto> findByIdList(List<Integer> idList){
        List<StorageTank> all = repository.findByIdIn(idList);
        if(com.alibaba.excel.util.CollectionUtils.isEmpty(all)){
            return null;
        }
        List<StorageTankDto> list = copyList(all);
        return list;
    }


    private List<StorageTankDto> copyList(List<StorageTank> storageTank){
        List<StorageTankDto> list = new ArrayList<>();
        storageTank.forEach(item->{
            StorageTankDto dto = new StorageTankDto();
            //把item 中 与dto 属性名称相同且值不为空的 复制给dto
            CopyUtils.copyProperties(item,dto);
            list.add(dto);
        });
        return list;
    }

    public void downloadModelExcel(HttpServletResponse response, String fileName, String station, Class<StorageTankDto> storageTankDtoClass, ExcelTypeEnum xlsx) throws IOException {
//        List<StorageTank> stations = storageTankDao.findModelExcel();
//        List<StorageTankDto> list = copyList(stations);
//        String s = JSON.toJSONString(list);
//        log.info("模板:"+s);
        String model = "{\"setup_time\":\"2020-04-22 04:54:18\",\"address\":\"3\",\"areaCode\":\"4\",\"catchmentArea\":12,\"inflow\":10.0000,\"initialDepth\":2.0000,\"insoleElevation\":12.0000,\"lat\":1110.000000,\"lon\":1110.000000,\"maxDepth\":12.0000,\"overloadDepth\":3.0000,\"tankCode\":\"13\",\"tankName\":\"TEST\"}";
        List<StorageTankDto> list = new ArrayList<>();
        StorageTankDto dto = JSON.parseObject(model,StorageTankDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list, fileName, station, storageTankDtoClass, xlsx);
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
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

    public InterceptingStatisticsDto getStatisticsDto() {
        Map<String, Object> map = repository.getStatisticsDto();
        InterceptingWellStatisticsDto statistics = JSONArray.parseObject(JSON.toJSONString(map),InterceptingWellStatisticsDto.class);
        InterceptingStatisticsDto dto = new InterceptingStatisticsDto();

        List<StatisticsVarCherDto> insoleList = new ArrayList<>();
        List<StatisticsVarCherDto> flowList = new ArrayList<>();
        List<StatisticsVarCherDto> areaList = new ArrayList<>();
        insoleList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_INSOLE_MAX, statistics.getInsoleMax()));
        insoleList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_INSOLE_MIN, statistics.getInsoleMin()));
        flowList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_FLOW_MAX,statistics.getFlowMax()));
        flowList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_FLOW_MIN,statistics.getFlowMin()));
        areaList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_AREA_MAX,statistics.getAraeMax()));
        areaList.add(new StatisticsVarCherDto(Constants.INTERCEPTING_WELL_AREA_MIN,statistics.getAraeMin()));
        dto.setAreaList(areaList);
        dto.setFlowList(flowList);
        dto.setInsoleList(insoleList);
        return dto;
    }

}
