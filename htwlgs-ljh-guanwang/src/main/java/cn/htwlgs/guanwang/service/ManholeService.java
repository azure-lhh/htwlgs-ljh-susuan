package cn.htwlgs.guanwang.service;

import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.ManholeDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.enums.ShapeTypeEnum;
import cn.htwlgs.guanwang.entity.Manhole;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.ManholeRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.BeanUtils;
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
import java.util.*;
import java.util.function.Consumer;

import cn.htwlgs.common.utils.CopyUtils;
/**
 * @ClassName ManholeService
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 9:55
 * @Version 1.0
 */
@Service
public class ManholeService {

    @Autowired
    private DataService dataService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ManholeRepository repository;


    public PageList queryPageAllByName( String keyWord, Integer pageNum, Integer pageSize){
        Page<Manhole> partPage = seletePagingByParam( keyWord, pageNum, pageSize);
        List<Manhole> list = partPage.getContent();
        if(CollectionUtils.isEmpty(list)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<ManholeDto> dtos = new ArrayList<>();
        list.forEach( c -> {
            ManholeDto manhole = new ManholeDto();
            BeanUtils.copyProperties(c,manhole);
            manhole.setMheShape(c.getMheShape().getVal());
            dtos.add(manhole);
        });
        return new PageList<>(dtos,pageNum,pageSize,partPage.getTotalElements());
    }

    public Page<Manhole> seletePagingByParam( String keyWord, Integer pageNum, Integer pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "setupTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return repository.findAllByNameLike(keyWord, pageable);
//            return repository.findAllByNameLike(keyWord, pageable);
        }
        return repository.findAll(pageable);
    }


    public void saveManhole(List<ManholeDto>  list){
        if (org.springframework.util.CollectionUtils.isEmpty(list)){
            throw new BusinessException("数据为空");
        }
        List<Manhole> manholes = new ArrayList<>();

        list.forEach( c -> {
               Manhole manhole = new Manhole();
            BeanUtils.copyProperties(c,manhole);
            manhole.setMheShape(ShapeTypeEnum.getEnumData(c.getMheShape()));
            manholes.add(manhole);
        });
        repository.saveAll(manholes);

    }

    @Transactional(rollbackFor = Exception.class)
    public void create(ManholeDto dto){
        Optional<Manhole> byId = repository.findBySerialNo(dto.getSerialNo());
        if(!byId.isPresent()){
            throw new BusinessException(Constants.SERIAL_NO_EXIST_ERROR);
        }
        Manhole manhole = new Manhole();
        BeanUtils.copyProperties(dto,manhole);
        manhole.setMheShape(ShapeTypeEnum.getEnumData(dto.getMheShape()));
        repository .save(manhole);
    }

    public ManholeDto findById(Integer id) {
        Optional<Manhole> opt = repository.findById(id);
        if(!opt.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        Manhole byId = opt.get();
        ManholeDto dto = new ManholeDto();
        BeanUtils.copyProperties(byId,dto);
        dto.setMheShape(byId.getMheShape().getVal());
        return dto;
    }

    public void update(ManholeDto dto){

        ManholeDto byId = findById(dto.getId());
        if (byId ==null){
            throw new BusinessException("编号不存在");
        }
        Manhole manhole = new Manhole();
        CopyUtils.copyProperties(dto,manhole);
        manhole.setMheShape(ShapeTypeEnum.getEnumData(dto.getMheShape()));
         repository.saveAndFlush(manhole );
    }


    public int deleteByIdIn(List<Integer> ids){
          return  repository.deleteAllByIdIn(ids);
    }


    public void importExcel( MultipartFile file) throws IOException {
        AnalysisEventListener<ManholeDto > userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),2);
        EasyExcel.read(file.getInputStream(), ManholeDto.class, userAnalysisEventListener).sheet().doRead();
    }


    public void exportTemeplate(HttpServletResponse response, String fileName, String station, Class<?> procls, ExcelTypeEnum xlsx) throws IOException {
        List<ManholeDto> list = new ArrayList<>();
        ManholeDto manholeDto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"name\":\"名称\",\"area_code\":\"行政区编码\",\"address\":\"检查井地址\",\"serial_no\":\"检查井编号\",\"depth\":\"22.6\",\"man_unit\":\"管理单位\",\"material\":\"井材质\",\"deleted\":\"1\",\"lon\":\"0\",\"lat\":\"0\",\"mhe_shape\":\"0\"}",ManholeDto.class);
        list.add(manholeDto);
        EasyExcelUtil.exportExcel(response, list, fileName, station, procls, xlsx);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum typeEnum, List<Integer> idList) throws IOException {
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<Manhole> all = repository.findAllByIdIn(idList);
        List<ManholeDto> list = new ArrayList<>();
        all.forEach( c -> {
            ManholeDto manhole = new ManholeDto();
            BeanUtils.copyProperties(c,manhole);
            manhole.setMheShape(c.getMheShape().getVal());
            list.add(manhole);
        });
        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("date");
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, typeEnum);
    }



    public Consumer<List<ManholeDto>> batchInsert(){
        return list -> saveManhole(list);
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
    }

    public List<StatisticsVarCherDto> groupByState() {
        List<Map<String, Object>> groupByAreaCode =  repository.getGroupByState();
        List<StatisticsVarCherDto> byList = JSONArray.parseArray(JSON.toJSONString(groupByAreaCode), StatisticsVarCherDto.class);
        return dataService.groupByState(byList);
    }


    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }
}
