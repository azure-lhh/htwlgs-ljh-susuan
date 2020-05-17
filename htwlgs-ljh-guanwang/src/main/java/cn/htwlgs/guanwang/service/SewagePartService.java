package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.dtos.enums.*;
import cn.htwlgs.guanwang.entity.SewagePart;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.SewagePartRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

/**
 * @ClassName SewagePartService
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 17:58
 * @Version 1.0
 */
@Service
public class SewagePartService {


    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;

    @Autowired
    SewagePartRepository repository;

    public PageList queryPageAllByName( String areaCode,String keyWord, Integer pageNum, Integer pageSize){
        Page<SewagePart> partPage = seletePagingByParam(keyWord,areaCode,pageNum,pageSize);
        List<SewagePart> list = partPage.getContent();
        if(CollectionUtils.isEmpty(list)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<SewagePartDto> dtos = new ArrayList<>();
        list.forEach( c -> {
            dtos.add(new SewagePartDto(c));
        });
        return new PageList<>(dtos,pageNum,pageSize,partPage.getTotalElements());
    }

    public Page<SewagePart> seletePagingByParam(String areaCode, String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)&&StringUtils.hasLength(areaCode)){
            return repository.findAllByAreaCodeAndPipeNameLike(areaCode,"%"+keyWord+"%", pageable);
        }
        if (StringUtils.hasLength(areaCode)){
            return repository.findAllByAreaCode(areaCode, pageable);
        }
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByPipeNameLike("%"+keyWord+"%", pageable);
        }
        return repository.findAll(pageable);
    }




    public List<SewagePart> findByIdIn(List<Integer> idList){
        List<SewagePart> all = repository.findByIdIn(idList);
        return all;
    }


    public void saveSewageOutlet(List<SewagePartDto>  list){
        List<String> strings = new  ArrayList();
        for (SewagePartDto  part:list) {
            strings.add(part.getPipeNum());
        }
        List<Map<String, Object>> allByPartNumIn = repository.findAllByPartNumIn(strings);
        commonService.isgGWPartNum(allByPartNumIn);
        List<SewagePart> sewageParts = new ArrayList<>();
        list.forEach( c -> {
            sewageParts.add(new SewagePart(c));
        });
        repository.saveAll(sewageParts);
    }


    @Transactional(rollbackFor = Exception.class)
    public void create(SewagePartDto dto){
        List<Map<String, Object>> allByPartNumIn = repository.findAllByPartNum(dto.getPipeNum());
        commonService.isgGWPartNum(allByPartNumIn);
        repository.save(new SewagePart(dto));
    }

    public SewagePartDto findById(Integer id) {
        Optional<SewagePart> byId = repository.findById(id);
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new SewagePartDto(byId.get());
    }
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    public void update(SewagePartDto dto){
        Optional<SewagePart> data = repository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        repository.saveAndFlush(data.get());
    }


    public int deleteByIdIn(List<Integer> ids){
        return repository.deleteAllByIdIn(ids);
    }





    public void importExcel( MultipartFile file) throws IOException {
        AnalysisEventListener<SewagePartDto > userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),2);
        EasyExcel.read(file.getInputStream(), SewagePartDto.class, userAnalysisEventListener).sheet().doRead();
    }


    public Consumer<List<SewagePartDto>> batchInsert(){
        return list -> saveSewageOutlet(list);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum typeEnum, List<Integer> idList) throws IOException {
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<SewagePart> all = this.findByIdIn(idList);
        if(CollectionUtils.isEmpty(all)){
            throw  new BusinessException("空数据");
        }
        List<SewagePartDto> list = new ArrayList<>();
        all.forEach( c -> {
            list.add(new SewagePartDto(c));
        });
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, typeEnum);

    }

    public void exportTemeplate(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum xlsx) throws IOException {
        List<SewagePartDto> list = new ArrayList<>();
        SewagePartDto sewagePartDto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"pipe_name\":\"污水管段名称\",\"pipe_num\":\"管道编号\",\"area_code\":\"行政区划编码\",\"level\":\"一级\",\"length\":\"0\",\"address\":\"\",\"lon\":\"0\",\"lat\":\"0\",\"relationship\":\"空间拓扑关系\",\"equipment\":\"设备基础信息\",\"shape\":\"圆形\",\"within\":\"5.3\",\"outside\":\"2.3\",\"depth\":\"10\",\"effluent\":\"5\",\"enter\":\"5\",\"upper_node\":\"上游节点\",\"lower_node\":\"下游节点\",\"upper_elevation\":\"0\",\"lower_elevation\":\"0\",\"create_user\":\"253.3\",\"update_user\":\"\",\"operate_id\":\"运营单位id\",\"deleted\":\"0\"}", SewagePartDto.class);
        list.add(sewagePartDto);
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, xlsx);
    }



    public List<TypeNameDto<String>>  loadDataTypeEnum(){
        List<TypeNameDto<String>> list  = new ArrayList<>();
        List<String>  list0 =   new ArrayList<>();
        for(GWLevelEnum levelEnum:GWLevelEnum.values()) {
            list0.add(levelEnum.getVal());
        }
        list.add(new TypeNameDto(0,"网管级别",list0));
        List<String>  list1 =   new ArrayList<>();
        for(ShapeTypeEnum typeEnum:ShapeTypeEnum.values()) {
            list1.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(1,"形状",list1));

        List<String>  list2 =   new ArrayList<>();
        for(PFTypeEnum typeEnum: PFTypeEnum.values()) {
            list2.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(2,"排污口排放方式",list2));

        List<String>  list3 =   new ArrayList<>();
        for(PFWayEnum typeEnum: PFWayEnum.values()) {
            list3.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(3,"排污口排放类型",list3));

        List<String>  list4 =   new ArrayList<>();
        for(SewageTypeEnum typeEnum: SewageTypeEnum.values()) {
            list4.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(4,"排污口类型",list4));

        List<String>  list5 =   new ArrayList<>();
        for(AttachmentTypeEnum typeEnum: AttachmentTypeEnum.values()) {
            list5.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(5,"工程区分",list5));


        List<String>  list6 =   new ArrayList<>();
        for(EngTypeEnum typeEnum: EngTypeEnum.values()) {
            list6.add(typeEnum.getVal());
        }
        list.add(new TypeNameDto(6,"子工程分类",list6));

        return list;
    }



    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }


    public List<Integer> findAllId() {
        return repository.getIdList();
    }


    public List<DataDto> findAll(){
        List<SewagePart> partRadiis = repository.findAll();
        return commonService.getPartRadiis(partRadiis);
    }

    public RainwaterStatisticsDto getStatisticsDto() {
        Map<String, Object> map = repository.getStatisticsDto();
        RainwaterStatisticsEntityDto statistics = JSON.parseObject(JSON.toJSONString(map), RainwaterStatisticsEntityDto.class);
        RainwaterStatisticsDto dto = new RainwaterStatisticsDto();
        dto.setLengthSum(statistics.getLengthSum());
        dto.setLengthAdd(statistics.getLengthAdd());
        List<StatisticsVarCherDto> depthList = new ArrayList<>();
        depthList.add(new StatisticsVarCherDto(Constants.RAINWATER_PART_STATISTICS_DEPTH_MAX,statistics.getDepthMax()));
        depthList.add(new StatisticsVarCherDto(Constants.RAINWATER_PART_STATISTICS_DEPTH_MIN,statistics.getDepthMin()));
        dto.setDepthList(depthList);
        return dto;
    }

    public List<StatisticsVarCherDto> getLeaveList() {
        List<Map<String, Object>> mapList = repository.getLevelList();
        List<StatisticsVarCherDto> list = JSONArray.parseArray(JSON.toJSONString(mapList), StatisticsVarCherDto.class);
        List<StatisticsVarCherDto> levelList = commonService.getLeaveListByGWLevelEnum(list);
        return levelList;
    }

    public List<StatisticsVarCherDto> getGroupByState() {
            List<Map<String, Object>> groupByState = repository.getGroupByState();
            List<StatisticsVarCherDto> list = JSONArray.parseArray(JSON.toJSONString(groupByState), StatisticsVarCherDto.class);
        return dataService.groupByState(list);
    }



}
