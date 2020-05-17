package cn.htwlgs.guanwang.service;


import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.SewagePointDto;
import cn.htwlgs.guanwang.dtos.SewagePointListDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.enums.GWLevelEnum;
import cn.htwlgs.guanwang.dtos.enums.ShapeTypeEnum;
import cn.htwlgs.guanwang.entity.SewageSpot;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.SewageSpotRepository;
import cn.htwlgs.guanwang.utils.*;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


@Service
public class SewagePointService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private SewageSpotRepository repository;


    public PageList<SewagePointListDto> getPageList(String keyWord, String areaCode, Integer pageNum, Integer pageSize) {
        Page<SewageSpot> page = getPageByParam(keyWord,areaCode,pageNum,pageSize);
        List<SewageSpot> parts = page.getContent();
        if(org.springframework.util.CollectionUtils.isEmpty(parts)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<SewagePointListDto> dtos = new ArrayList<>();
        parts.forEach( c -> {
            dtos.add(new SewagePointListDto(c));
        });
        return new PageList(dtos,pageNum,pageSize,page.getTotalElements());
    }

    public Page<SewageSpot> getPageByParam(String keyWord, String areaCode, Integer pageNum, Integer pageSize){
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

    public List<SewageSpot> findByIdList(List<Integer> idList){
        List<SewageSpot> all = repository.findByIdIn(idList);
        return all;
    }


    public void saveSewageOutlet(List<SewagePointDto>  list){

        List<SewageSpot> sewageSpots = new ArrayList<>();
        list.forEach( c -> {
            SewageSpot sewageSpot = new SewageSpot();
            BeanUtils.copyProperties(c,sewageSpot);
            sewageSpot.setLevel(GWLevelEnum.getEnumData(c.getLevel()));
            sewageSpot.setShape(ShapeTypeEnum.getEnumData(c.getShape()));
            sewageSpot.setCreateUser(UserUtils.getUserName());
            sewageSpot.setCreateTime(LocalDateTime.now());
            sewageSpots.add(sewageSpot);
        });
        repository.saveAll(sewageSpots);

    }


    public void create(SewagePointDto dto){
        Optional<SewageSpot> byId = repository.findByPipeNum(dto.getPipeNum());
        if(byId.isPresent()){
            throw new BusinessException(Constants.SERIAL_NO_EXIST_ERROR);
        }
        SewageSpot sewageSpot = new SewageSpot();
        BeanUtils.copyProperties(dto,sewageSpot);
        sewageSpot.setCreateUser(UserUtils.getUserName()); // 创建人暂时无法获取
        sewageSpot.setDeleted(Long.valueOf(0));
        sewageSpot.setLevel(GWLevelEnum.getEnumData(dto.getLevel()));
        sewageSpot.setShape(ShapeTypeEnum.getEnumData(dto.getShape()));
        repository.save(sewageSpot);
    }

    public SewagePointListDto findById(String id) {
        Optional<SewageSpot> spot = repository.findById(Integer.valueOf(id));
        if(!spot.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        SewagePointListDto dto = new SewagePointListDto();
        SewageSpot byId =spot.get();
        BeanUtils.copyProperties(byId,dto);
        dto.setShape(byId.getShape().getVal());
        dto.setLevel(byId.getLevel().getVal());
        return dto;
    }


    public void update(SewagePointDto dto){
        Optional<SewageSpot> byId = repository.findById(dto.getId());
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,byId.get());
        byId.get().setUpdateUser(UserUtils.getUserName()); // 暂时无法获取
        byId.get().setLevel(GWLevelEnum.getEnumData(dto.getLevel()));
        byId.get().setShape(ShapeTypeEnum.getEnumData(dto.getShape()));
        repository.saveAndFlush(byId.get());
    }


    public int deleteByIdIn(List<Integer> ids){
        return repository.deleteByIdIn(ids);
    }





    public void importExcel( MultipartFile file) throws IOException {
        AnalysisEventListener<SewagePointDto > userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),2);
        EasyExcel.read(file.getInputStream(), SewagePointDto.class, userAnalysisEventListener).sheet().doRead();
    }


    public Consumer<List<SewagePointDto>> batchInsert(){
        return list -> saveSewageOutlet(list);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum typeEnum, List<Integer> idList) throws IOException {
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<SewageSpot> all = this.findByIdList(idList);
        /*if(CollectionUtils.isEmpty(all)){
            throw  new BusinessException("数据为空");
        }*/
        List<SewagePointDto> list = new ArrayList<>();
        all.forEach( c -> {
            SewagePointDto sewagePointDto = new SewagePointDto();
            BeanUtils.copyProperties(c,sewagePointDto);
            sewagePointDto.setShape(c.getShape().getVal());
            sewagePointDto.setLevel(c.getLevel().getVal());
            list.add(sewagePointDto);
        });
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, typeEnum);

    }

    public void exportTemeplate(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum xlsx) throws IOException {
        List<SewagePointDto> list = new ArrayList<>();
        SewagePointDto sewagePointDto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"name\":\"污水口名\",\"area_code\":\"行政编号\",\"address\":\"地址\",\"lon\":\"0.3\",\"lat\":\"0.3\",\"serial_no\":\"编号\",\"depth\":\"33.3\",\"type\":\"1\",\"scale\":\"规模\",\"pfway\":\"1\",\"SewageOutlet_type\":\"2\",\"reserves\":\"1\",\"update_time\":\"2020-02-22 10:49:59\",\"update_user\":\"54545\",\"create_user\":\"123\",\"create_time\":\"2020-02-22 10:49:59\",\"deleted\":\"1\",\"water_func\":\"x001\",\"water_region\":\"x001\",\"river\":\"长江\",\"remark\":\"备注\"}",SewagePointDto.class);
        list.add(sewagePointDto);
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, xlsx);
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
    }

    public List<StatisticsVarCherDto> getSetupTimeList() {
        List<LocalDateTime> dates = repository.getSetupTimeList();
        return commonService.getList(dates);
    }


}
