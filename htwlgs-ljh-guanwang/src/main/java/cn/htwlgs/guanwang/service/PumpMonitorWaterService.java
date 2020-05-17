package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpMonitorWaterDto;
import cn.htwlgs.guanwang.entity.PumpMonitorWater;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.PumpMonitorWaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PumpMonitorWaterService {

    @Autowired
    private PumpMonitorWaterRepository repository;

    @Transactional(rollbackFor = Exception.class)
    public void save(PumpMonitorWaterDto dto) {
        repository.save(new PumpMonitorWater(dto));
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAndFlush(PumpMonitorWaterDto dto) {
        Optional<PumpMonitorWater> data = repository.findById(dto.getId());
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

    public List<PumpMonitorWaterDto> getPageList(Integer stationId) {
        List<PumpMonitorWater> page = repository.findAllByStationId(stationId);
        List<PumpMonitorWaterDto> list = new ArrayList<>();
        page.forEach(item->{
            list.add(new PumpMonitorWaterDto(item));
        });
        return list;
    }
}