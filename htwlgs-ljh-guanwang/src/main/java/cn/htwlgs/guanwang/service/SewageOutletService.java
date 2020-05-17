package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.SewageOutletDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.enums.PFTypeEnum;
import cn.htwlgs.guanwang.dtos.enums.PFWayEnum;
import cn.htwlgs.guanwang.entity.SewageOutlet;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.SewageOutletRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
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
 * @ClassName SewageOutletService
 * @Description
 * @Author lihouhai
 * @Date 2020/4/21 11:20
 * @Version 1.0
 */
@Slf4j
@Service
public class SewageOutletService {


    @Autowired
    private CommonService commonService;
    @Autowired
    DataService dataService;

    @Autowired
    private SewageOutletRepository repository;


    public PageList queryPageAllByName( String keyWord, Integer pageNum, Integer pageSize){
        Page<SewageOutlet> partPage = seletePagingByParam( keyWord, pageNum, pageSize);
        List<SewageOutlet> list = partPage.getContent();
        if(CollectionUtils.isEmpty(list)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<SewageOutletDto> dtos = new ArrayList<>();
        list.forEach( c -> {
            dtos.add(new SewageOutletDto(c));
        });
        return new PageList<>(dtos,pageNum,pageSize,partPage.getTotalElements());
    }

    public Page<SewageOutlet> seletePagingByParam(String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }

    public List<SewageOutlet> findByIdList(List<Integer> idList){
        return repository.findAllByIdIn(idList);
    }

    //保存数据
    public void saveSewageOutlet(List<SewageOutletDto>  list){

        List<SewageOutlet> sewageOutlets = new ArrayList<>();
        list.forEach( c -> {
            sewageOutlets.add( new SewageOutlet(c));
        });
            repository .saveAll(sewageOutlets);

    }

    @Transactional(rollbackFor = Exception.class)
    public void create(SewageOutletDto dto){
        SewageOutlet sewageOutlet = new SewageOutlet(dto);
        repository.save(sewageOutlet);
    }

    //通过id查询
    public SewageOutletDto findById(Integer id) {
        Optional<SewageOutlet> byId = repository.findById(id);
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new SewageOutletDto(byId.get());
    }


    //修改
    public void update(SewageOutletDto dto){
        Optional<SewageOutlet> data = repository.findById(dto.getId());
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
        AnalysisEventListener<SewageOutletDto > userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),2);
        EasyExcel.read(file.getInputStream(), SewageOutletDto.class, userAnalysisEventListener).sheet().doRead();
    }

    //批量保存
    public Consumer<List<SewageOutletDto>> batchInsert(){
        return list -> saveSewageOutlet(list);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum typeEnum, List<Integer> idList) throws IOException {
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<SewageOutlet> all = this.findByIdList(idList);
        List<SewageOutletDto> list = new ArrayList<>();
        all.forEach(c -> {
            list.add(new SewageOutletDto(c));
        });
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, typeEnum);

    }
    //导出模板
    public void exportTemeplate(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum xlsx) throws IOException {
        List<SewageOutletDto> list = new ArrayList<>();
        SewageOutletDto sewageOutletDto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"name\":\"污水口名\",\"area_code\":\"行政编号\",\"address\":\"地址\",\"lon\":\"0.3\",\"lat\":\"0.3\",\"serial_no\":\"编号\",\"depth\":\"33.3\",\"type\":\"1\",\"scale\":\"规模\",\"pfway\":\"1\",\"sewage_type\":\"2\",\"reserves\":\"1\",\"update_user\":\"54545\",\"create_user\":\"123\",\"deleted\":\"1\",\"water_func\":\"x001\",\"water_region\":\"x001\",\"river\":\"长江\",\"remark\":\"备注\"}",SewageOutletDto.class);
        list.add(sewageOutletDto);
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, xlsx);
    }


    public List<Integer> findAllId() {
        return repository.findAllId();
    }


    public List<StatisticsVarCherDto> groupByState() {
        List<Map<String, Object>> groupByAreaCode = repository.getGroupByAreaCode();
        List<StatisticsVarCherDto> byList = JSONArray.parseArray(JSON.toJSONString(groupByAreaCode), StatisticsVarCherDto.class);
        return dataService.groupByState(byList);
    }

    public List<StatisticsVarCherDto> getGroupByType() {
        List<Map<String, Object>> groupByAreaCode = repository.getGroupByType();
        List<StatisticsVarCherDto> byList = JSONArray.parseArray(JSON.toJSONString(groupByAreaCode), StatisticsVarCherDto.class);
        return tyepToName(byList);
    }

    public List<StatisticsVarCherDto> getGroupByPFWay() {
        List<Map<String, Object>> groupByAreaCode =  repository.getGroupByPFWay();
        List<StatisticsVarCherDto> byList = JSONArray.parseArray(JSON.toJSONString(groupByAreaCode), StatisticsVarCherDto.class);
        return tyepToPfway(byList);
    }

    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }


    public List<StatisticsVarCherDto> tyepToName(List<StatisticsVarCherDto>  list){
        if (org.springframework.util.CollectionUtils.isEmpty(list)){
            throw  new BusinessException("排污口数据为空");
        }
        List<StatisticsVarCherDto>  dtos = new ArrayList<>();
        for (StatisticsVarCherDto  c:list) {
            for (PFTypeEnum typeEnum:PFTypeEnum.values()) {
                if (c.getName().equals(String.valueOf(typeEnum.ordinal()))){
                    dtos.add( new StatisticsVarCherDto(typeEnum.getVal(),c.getNum()));
                }
            }
        }
        return dtos;
    }


    public List<StatisticsVarCherDto> tyepToPfway(List<StatisticsVarCherDto>  list){
        if (org.springframework.util.CollectionUtils.isEmpty(list)){
            throw  new BusinessException("排污口数据为空");
        }
        List<StatisticsVarCherDto>  dtos = new ArrayList<>();
        for (StatisticsVarCherDto  c:list) {
            for (PFWayEnum typeEnum:PFWayEnum.values()) {
                if (c.getName().equals(String.valueOf(typeEnum.ordinal()))){
                    dtos.add( new StatisticsVarCherDto(typeEnum.getVal(),c.getNum()));
                }
            }
        }
        return dtos;
    }


}
