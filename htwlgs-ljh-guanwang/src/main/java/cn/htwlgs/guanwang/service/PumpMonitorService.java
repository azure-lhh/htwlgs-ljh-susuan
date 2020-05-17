package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpMonitorDto;
import cn.htwlgs.guanwang.dtos.PumpMonitorPageDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.entity.PumpMonitor;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.PumpMonitorDataRepository;
import cn.htwlgs.guanwang.repository.PumpMonitorRepository;
import cn.htwlgs.guanwang.utils.PageList;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PumpMonitorService {

    @Autowired
    private PumpMonitorRepository repository;
    @Autowired
    private PumpMonitorDataRepository dataRepository;

    public void save(PumpMonitorDto dto) {
        repository.save(new PumpMonitor(dto));
    }

    public void saveAndFlush(PumpMonitorDto dto) {
        Optional<PumpMonitor> data = repository.findById(dto.getId());
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


    @SuppressWarnings("unchecked")
    public PageList<PumpMonitorDto> getPageList(String name, String area, Integer pageNum, Integer pageSize) {
        Page<PumpMonitor> page = getPageByParam(name,area,pageNum,pageSize);
        List<PumpMonitor> monitors = page.getContent();
        if(CollectionUtils.isEmpty(monitors)){
            return new PageList<>(null,pageNum,pageSize,repository.count());
        }
        List<PumpMonitorDto> list = new ArrayList();
        monitors.forEach(item->{
            list.add(new PumpMonitorDto(item));
        });
        return new PageList(list,pageNum,pageSize,page.getTotalElements());
    }

    public Page<PumpMonitor> getPageByParam(String stationName, String areaCode,Integer pageNum, Integer pageSize) {
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

    public PumpMonitorDto findById(Integer stationId) {
        Optional<PumpMonitor> opt = repository.findById(Integer.valueOf(stationId));
        if(!opt.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        return new PumpMonitorDto(opt.get());
    }

    public PageList<PumpMonitorPageDto> getPumpMonitorPageDto(String name, String area, LocalDate date, LocalTime time, Integer pageNum, Integer pageSize) {
        long count = dataRepository.count();
        if(count < 1){
            return new PageList<>(null,pageNum,pageSize,count);
        }
        List<Map<String, Object>> maps = dataRepository.getMonitorList(name,area,date,time,(pageNum-1)*pageSize,pageSize);
        if(CollectionUtils.isEmpty(maps)){
            return new PageList<>(null,pageNum,pageSize,count);
        }
        List<PumpMonitorPageDto> data = JSONArray.parseArray(JSON.toJSONString(maps), PumpMonitorPageDto.class);
        List<PumpMonitorPageDto> list = new ArrayList();
        data.forEach(item->{
            list.add(getLevelMonitorPageDto(item));
        });
        return new PageList(list,pageNum,pageSize,count);
    }

    public PumpMonitorPageDto getLevelMonitorPageDto(PumpMonitorPageDto dto){
        List<Map<String, Object>> storageMaps = dataRepository.storageList(dto.getId());
        List<Map<String, Object>> trafficMaps = dataRepository.getTrafficList(dto.getId());
        List<StatisticsVarCherDto> storageList = JSONArray.parseArray(JSON.toJSONString(storageMaps), StatisticsVarCherDto.class);
        List<StatisticsVarCherDto> trafficList = JSONArray.parseArray(JSON.toJSONString(trafficMaps), StatisticsVarCherDto.class);
        dto.setTrafficList(trafficList);
        dto.setStorageList(storageList);
        return dto;
    }
}