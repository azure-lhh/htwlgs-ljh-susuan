package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.RainwaterSpotDto;
import cn.htwlgs.guanwang.entity.RainwaterSpot;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.RainwaterSpotRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class RainwaterSpotService {

    @Autowired
    private RainwaterSpotRepository repository;

    public RainwaterSpotDto findById(Integer id) {
        Optional<RainwaterSpot> spot = repository.findById(id);
        if(!spot.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new RainwaterSpotDto(spot.get());
    }

    public PageList<RainwaterSpotDto> getPageList(String keyWord, String area, Integer pageNum, Integer pageSize) {
        Page<RainwaterSpot> page = getPageByParam(keyWord,area,pageNum,pageSize);
        List<RainwaterSpot> parts = page.getContent();
        if(CollectionUtils.isEmpty(parts)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<RainwaterSpotDto> list = copyList(parts);
        return new PageList(list,pageNum,pageSize,page.getTotalElements());
    }

    public Page<RainwaterSpot> getPageByParam(String keyWord, String areaCode, Integer pageNum, Integer pageSize){
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

    private List<RainwaterSpotDto> copyList(List<RainwaterSpot> parts){
        List<RainwaterSpotDto> list = new ArrayList<>();
        parts.forEach(item->{
            list.add(new RainwaterSpotDto(item));
        });
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(RainwaterSpotDto dto) {
        Optional<RainwaterSpot> byId = repository.findByPipeNum(dto.getPipeNum());
        if(byId.isPresent()){
            throw new BusinessException(Constants.SERIAL_NO_EXIST_ERROR);
        }
        repository.save(new RainwaterSpot(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void modify(RainwaterSpotDto dto) {
        Optional<RainwaterSpot> data = repository.findById(dto.getId());
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
        AnalysisEventListener<RainwaterSpotDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsert(),50);
        EasyExcel.read(file.getInputStream(), RainwaterSpotDto.class, userAnalysisEventListener).sheet().doRead();
    }

    public Consumer<List<RainwaterSpotDto>> batchInsert(){
        return list -> saveRainwaterSpot(list);
    }

    private void saveRainwaterSpot(List<RainwaterSpotDto> list) {
        if (CollectionUtils.isEmpty(list)){
            throw new BusinessException(Constants.DATA_NULL_ERROR);
        }
        List<RainwaterSpot> parts = new ArrayList<>();
        list.forEach(item->{
            parts.add(new RainwaterSpot(item));
        });
        repository.saveAll(parts);
    }

    public void exportExcel(HttpServletResponse response, String fileName, String part, Class<RainwaterSpotDto> RainwaterSpotDto, ExcelTypeEnum xlsx, List<Integer> idList) throws IOException {
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException(Constants.EXPORT_DATA_NULL_ERROR);
        }
        List<RainwaterSpotDto> list = findByIdList(idList);
        EasyExcelUtil.exportExcel(response, list,fileName , part, RainwaterSpotDto,xlsx);
    }

    public List<RainwaterSpotDto> findByIdList(List<Integer> idList){
        List<RainwaterSpot> tanks = repository.findByIdIn(idList);
        List<RainwaterSpotDto> list = copyList(tanks);
        return list;
    }

    public void downloadExcel(HttpServletResponse response, String fileName, String part, Class<RainwaterSpotDto> RainwaterSpotDto, ExcelTypeEnum xlsx) throws IOException {
        List<RainwaterSpotDto> list = new LinkedList<>();
        RainwaterSpotDto dto = JSON.parseObject("{\"setup_time\":\"2020-04-22 04:54:18\",\"id\":\"1\",\"pipe_name\":\"雨水管点kfh1\",\"pipe_num\":\"1\",\"area_code\":\"100\",\"level\":\"一级\",\"length\":\"0\",\"address\":\"重庆\",\"lon\":\"8.3\",\"lat\":\"5.3\",\"relationship\":\"\",\"equipment\":\"\",\"shape\":\"圆形\",\"within\":\"0\",\"outside\":\"0\",\"depth\":\"0\",\"effluent\":\"0\",\"enter\":\"0\",\"upper_node\":\"宋家堡\",\"lower_node\":\"杨家庙\",\"upper_elevation\":\"0\",\"lower_elevation\":\"0\",\"create_user\":\"王大伟\",\"operate_id\":\"1\"}",RainwaterSpotDto.class);
        list.add(dto);
        EasyExcelUtil.exportExcel(response, list,fileName , part, RainwaterSpotDto,xlsx);
    }

    public List<Integer> findAllId() {
        return repository.findAllId();
    }
}
