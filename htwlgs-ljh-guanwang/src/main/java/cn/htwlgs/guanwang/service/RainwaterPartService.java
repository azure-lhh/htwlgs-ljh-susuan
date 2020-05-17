package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.RainwaterPart;
import cn.htwlgs.guanwang.entity.SewagePart;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.RainwaterPartRepository;
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
public class RainwaterPartService {

    @Autowired
    private CommonService commonService;
    @Autowired
    private DataService dataService;

    @Autowired
    RainwaterPartRepository repository;


    public RainwaterPartDto findById(Integer id) {
        Optional<RainwaterPart> part = repository.findById(id);
        if(!part.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new RainwaterPartDto(part.get());
    }

    public PageList<RainwaterPartDto> getPageList(String name, String area, Integer pageNum, Integer pageSize) {
        Page<RainwaterPart> page = getPageByParam(name,area,pageNum,pageSize);
        List<RainwaterPart> parts = page.getContent();
        if(CollectionUtils.isEmpty(parts)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<RainwaterPartDto> list = copyList(parts);
        return new PageList(list,pageNum,pageSize,page.getTotalElements());
    }

    public Page<RainwaterPart> getPageByParam(String keyWord, String areaCode, Integer pageNum, Integer pageSize){
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

    private List<RainwaterPartDto> copyList(List<RainwaterPart> parts){
        List<RainwaterPartDto> list = new ArrayList<>();
        parts.forEach(item->{
            list.add(new RainwaterPartDto(item));
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(RainwaterPartDto dto) {
        List<Map<String, Object>> allByPartNum = repository.findAllByPartNum(dto.getPipeNum());
        commonService.isgGWPartNum(allByPartNum);
        repository.save(new RainwaterPart(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modify(RainwaterPartDto dto) {
        Optional<RainwaterPart> data = repository.findById(dto.getId());
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
        AnalysisEventListener<RainwaterPartDto> userAnalysisEventListener = EasyExcelListener.getListener(batchInsert(),50);
        EasyExcel.read(file.getInputStream(), RainwaterPartDto.class, userAnalysisEventListener).sheet().doRead();
    }

    public Consumer<List<RainwaterPartDto>> batchInsert(){
        return list -> saveRainwaterPart(list);
    }

    private void saveRainwaterPart(List<RainwaterPartDto> list) {
        List<String> strings = new  ArrayList();
        for (RainwaterPartDto  part:list) {
            strings.add(part.getPipeNum());
        }
        List<Map<String, Object>>  allByPartNumIn = repository.findAllByPartNumIn(strings);
        commonService.isgGWPartNum(allByPartNumIn);
        List<RainwaterPart> parts = new ArrayList<>();
        list.forEach(item->{
            parts.add(new RainwaterPart(item));
        });
        repository.saveAll(parts);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String part, Class<RainwaterPartDto> rainwaterPartDto, ExcelTypeEnum xlsx, List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException(Constants.EXPORT_DATA_NULL_ERROR);
        }
        List<RainwaterPartDto> list = findByIdList(idList);
        EasyExcelUtil.exportExcel(response, list,fileName , part, rainwaterPartDto,xlsx);
    }

    public List<RainwaterPartDto> findByIdList(List<Integer> idList){
        List<RainwaterPart> tanks = repository.findByIdIn(idList);
        List<RainwaterPartDto> list = copyList(tanks);
        return list;
    }

    public void downloadExcel(HttpServletResponse response, String fileName, String part, Class<RainwaterPartDto> rainwaterPartDto, ExcelTypeEnum xlsx) throws IOException {
        List<RainwaterPartDto> list = new LinkedList<>();
        RainwaterPartDto dto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"pipe_name\":\"雨水管段mk1\",\"pipe_num\":\"1\",\"area_code\":\"100\",\"level\":\"一级\",\"length\":\"0\",\"address\":\"重庆\",\"lon\":\"8.3\",\"lat\":\"5.3\",\"relationship\":\"\",\"equipment\":\"\",\"shape\":\"圆形\",\"within\":\"0\",\"outside\":\"0\",\"depth\":\"0\",\"effluent\":\"0\",\"enter\":\"0\",\"upper_node\":\"宋家堡\",\"lower_node\":\"杨家庙\",\"upper_elevation\":\"0\",\"lower_elevation\":\"0\",\"operate_id\":\"1\"}",RainwaterPartDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list,fileName , part, rainwaterPartDto,xlsx);
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

    public RainwaterStatisticsDto getStatisticsDto() {
        Map<String, Object> map = repository.getStatisticsDto();
        RainwaterStatisticsEntityDto statistics = JSONArray.parseObject(JSON.toJSONString(map),RainwaterStatisticsEntityDto.class);
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
        List<Map<String, Object>> maps = repository.getLevelList();
        List<StatisticsVarCherDto> list = JSONArray.parseArray(JSON.toJSONString(maps), StatisticsVarCherDto.class);
        List<StatisticsVarCherDto> levelList = commonService.getLeaveListByGWLevelEnum(list);
        return levelList;
    }

    public List<DataDto> getPartRadiis(){
        List<Map<String, Object>> maps = repository.getPartRadiis();
        List<SewagePart> list = JSONArray.parseArray(JSON.toJSONString(maps), SewagePart.class);
        return commonService.getPartRadiis(list);
    }
}
