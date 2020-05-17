package cn.htwlgs.guanwang.service;

import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.InterceptingWell;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.InterceptingWellRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Slf4j
public class InterceptingWellService {
    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;
    @Autowired
    private InterceptingWellRepository repository;


    public PageList<InterceptingWellDto> getPageList(String name, Integer pageNum, Integer pageSize) {

        Page<InterceptingWell> partPage = seletePagingByParam(name, pageNum, pageSize);
        List<InterceptingWell> list = partPage.getContent();
        if(com.alibaba.excel.util.CollectionUtils.isEmpty(list)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<InterceptingWellDto> dtos = new ArrayList<>();
        list.forEach(item -> {
            InterceptingWellDto dto = new InterceptingWellDto();
            //把item 中 与dto 属性名称相同且值不为空的 复制给dto
            CopyUtils.copyProperties(item, dto);
            dtos.add(dto);

        });
        return new PageList(list, pageNum, pageSize, partPage.getTotalElements());
    }


    public Page<InterceptingWell> seletePagingByParam( String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByWellNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }


    public InterceptingWellDto findwellById(String id) {

        Optional<InterceptingWell> well = repository.findById(Integer.valueOf(id));
        if(!well.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        InterceptingWellDto wellDto = new InterceptingWellDto();

        BeanUtils.copyProperties(well.get(), wellDto);


        return wellDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(InterceptingWellListDto interceptingWellListDto) {
        Optional<InterceptingWell> byId = repository.findByWellCode(interceptingWellListDto.getWellCode());
        if(byId.isPresent()){
            throw new BusinessException(Constants.SERIAL_NO_EXIST_ERROR);
        }
        InterceptingWell gwInterceptingWell = new InterceptingWell();
        CopyUtils.copyProperties(interceptingWellListDto, gwInterceptingWell);
        gwInterceptingWell.setCreateTime(LocalDateTime.now());
        gwInterceptingWell.setCreateUser("admin"); // 创建人暂时无法获取
        gwInterceptingWell.setDeleted(Long.valueOf(0));
        repository.save(gwInterceptingWell);

    }

    public void modify(InterceptingWellListDto dto) {
        Optional<InterceptingWell> byId = repository.findById(dto.getId());
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setUpdateUser("测试");
        byId.get().setDeleted(Long.valueOf(0));
        byId.get().setUpdateTime(LocalDateTime.now());
        repository.saveAndFlush(byId.get());
    }

    public int remove(List<Integer> ids) {
        return repository.deleteAllByIdIn(ids);
    }


    public void importExcel(MultipartFile file) throws IOException {
        AnalysisEventListener<InterceptingWellListDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(), 2);
        EasyExcel.read(file.getInputStream(), InterceptingWellListDto.class, userAnalysisEventListener).sheet().doRead();
    }


    public Consumer<List<InterceptingWellListDto>> batchInsert() {
        return list -> batchSave(list);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String station, Class<InterceptingWellListDto> interceptingWellDtoClass, ExcelTypeEnum xlsx, List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<InterceptingWellListDto> list = findAll(idList);
        EasyExcelUtil.exportExcel(response, list, fileName, station, interceptingWellDtoClass, xlsx);
    }

    public void batchSave(List<InterceptingWellListDto> list) {
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException("数据为空");
        }
        List<InterceptingWell> wells = new ArrayList<>();
        list.forEach(item -> {
            InterceptingWell gwInterceptingWell = new InterceptingWell();
            CopyUtils.copyProperties(item, gwInterceptingWell);
            gwInterceptingWell.setCreateTime(LocalDateTime.now());
            gwInterceptingWell.setCreateUser("admin"); // 创建人暂时无法获取
            gwInterceptingWell.setDeleted(Long.valueOf(0));
            wells.add(gwInterceptingWell);

        });
        repository.saveAll(wells);
    }

    public List<InterceptingWellListDto> findAll(List<Integer> idList) {
        List<InterceptingWell> all = repository.findAllById(idList);
        if (com.alibaba.excel.util.CollectionUtils.isEmpty(all)) {
            return null;
        }
        List<InterceptingWellListDto> list = copyList(all);
        return list;
    }

    private List<InterceptingWellListDto> copyList(List<InterceptingWell> interceptingWell) {
        List<InterceptingWellListDto> list = new ArrayList<>();
        interceptingWell.forEach(item -> {
            InterceptingWellListDto dto = new InterceptingWellListDto();
            //把item 中 与dto 属性名称相同且值不为空的 复制给dto
            CopyUtils.copyProperties(item, dto);
            list.add(dto);
        });
        return list;
    }

    public void downloadModelExcel(HttpServletResponse response, String fileName, String station, Class<InterceptingWellListDto> interceptingWellListDtoClass, ExcelTypeEnum xlsx) throws IOException {
//        List<InterceptingWell> stations = wellDao.findModelExcel();
//        List<InterceptingWellListDto> list = copyList(stations);
//        String s = JSON.toJSONString(list);
//        log.info("模板:"+s);
        List<InterceptingWellListDto> list = new ArrayList<>();
        InterceptingWellListDto dto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"address\":\"test\",\"areaCode\":\"01\",\"catchmentArea\":1,\"inflow\":10.0000,\"initialDepth\":10.0000,\"insoleElevation\":10.0000,\"lat\":111.000000,\"lon\":1011.000000,\"maxDepth\":10.0000,\"overloadDepth\":10.0000,\"wellCode\":\"100\",\"wellName\":\"test\"}", InterceptingWellListDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list, fileName, station, interceptingWellListDtoClass, xlsx);
    }

    public List<Integer> findAllId() {
        return  repository.findAllId();
    }

    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }


    public List<StatisticsVarCherDto> getGroupByState() {
        List<Map<String, Object>>  maps = repository.findGroupByAreaCode();
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
