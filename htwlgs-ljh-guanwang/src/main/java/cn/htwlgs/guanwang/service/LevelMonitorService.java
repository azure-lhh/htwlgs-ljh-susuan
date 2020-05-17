package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.entity.LevelMonitor;
import cn.htwlgs.guanwang.entity.LevelMonitorData;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.LevelMonitorDataRepository;
import cn.htwlgs.guanwang.repository.LevelMonitorRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class LevelMonitorService {

    @Autowired
    private LevelMonitorRepository repository;
    @Autowired
    private LevelMonitorDataRepository dataRepository;

    public PageList<LevelMonitorDto> getPageList(String name, String area,Integer pageNum, Integer pageSize) {
        Page<LevelMonitor> page = getPageByParam(name,area,pageNum,pageSize);
        List<LevelMonitor> monitors = page.getContent();
        if(CollectionUtils.isEmpty(monitors)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<LevelMonitorDto> list = new ArrayList<>();
        monitors.forEach(item->{
            list.add(new LevelMonitorDto(item));
        });
        return new PageList(list,pageNum,pageSize,page.getTotalElements());
    }

    public Page<LevelMonitor> getPageByParam(String stationName, String areaCode,Integer pageNum, Integer pageSize) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if(!StringUtils.isEmpty(stationName) && !StringUtils.isEmpty(areaCode)){
            return repository.findAllByAreaCodeAndStationNameLike(areaCode,stationName,pageable);
        }
        if (!StringUtils.isEmpty(stationName)){
            return repository.findAllByStationNameLike(stationName,pageable);
        }
        if(!StringUtils.isEmpty(areaCode)){
            return repository.findAllByAreaCode(areaCode,pageable);
        }
        return repository.findAll(pageable);
    }

    public void save(LevelMonitorDto dto) {
        repository.save(new LevelMonitor(dto));
    }

    public void saveAndFlush(LevelMonitorDto dto) {
        Optional<LevelMonitor> data = repository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        repository.saveAndFlush(data.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Integer> idList) {
        repository.deleteByIdIn(idList);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveMonitorData(LevelMonitorDataDto dto) {
        dataRepository.save(new LevelMonitorData(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAndFlushMonitorData(LevelMonitorDataDto dto) {
        Optional<LevelMonitorData> data = dataRepository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        dataRepository.saveAndFlush(data.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteMonitorData(List<Integer> idList) {
        dataRepository.deleteByIdIn(idList);
    }

    public PageList<LevelMonitorPageDto> getLevelMonitorPageDto(String name, String area, LocalDate date, LocalTime time, Integer pageNum, Integer pageSize) {
        long count = dataRepository.count();
        if(count < 1){
            return new PageList<>(null,pageNum,pageSize,count);
        }
        List<Map<String, Object>> maps = dataRepository.getMonitorList(name,area,date,time,(pageNum-1)*pageSize,pageSize);
        if(CollectionUtils.isEmpty(maps)){
            return new PageList<>(null,pageNum,pageSize,count);
        }
        List<LevelMonitorPageDto> data = JSONArray.parseArray(JSON.toJSONString(maps), LevelMonitorPageDto.class);
        List<LevelMonitorPageDto> list = new ArrayList<>();
        data.forEach(item->{
            list.add(getLevelMonitorPageDto(item));
        });
        return new PageList(list,pageNum,pageSize,count);
    }

    public LevelMonitorPageDto getLevelMonitorPageDto(LevelMonitorPageDto dto){
        List<Map<String, Object>> wlMaps = dataRepository.getWlList(dto.getId());
        List<Map<String, Object>> trafficMaps = dataRepository.getTrafficList(dto.getId());
        List<StatisticsVarCherDto> wlList = JSONArray.parseArray(JSON.toJSONString(wlMaps), StatisticsVarCherDto.class);
        List<StatisticsVarCherDto> trafficList = JSONArray.parseArray(JSON.toJSONString(trafficMaps), StatisticsVarCherDto.class);
        dto.setTrafficList(trafficList);
        dto.setWlList(wlList);
        return dto;
    }

    public void importExcel(MultipartFile file) throws IOException {
        AnalysisEventListener<LevelMonitorDataDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsertMonitorData(),50);
        EasyExcel.read(file.getInputStream(), LevelMonitorDataDto.class, userAnalysisEventListener).sheet().doRead();
    }
    public Consumer<List<LevelMonitorDataDto>> batchInsertMonitorData(){
        return list -> saveMonitorData(list);
    }

    private void saveMonitorData(List<LevelMonitorDataDto> list) {
        List<LevelMonitorData> dataList = new ArrayList<>();
        list.forEach(item->{
            dataList.add(new LevelMonitorData(item));
        });
        dataRepository.saveAll(dataList);
    }

    public LevelMonitorDto findById(Integer stationId) {
        Optional<LevelMonitor> opt = repository.findById(Integer.valueOf(stationId));
        if(!opt.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new LevelMonitorDto(opt.get());
    }
}
