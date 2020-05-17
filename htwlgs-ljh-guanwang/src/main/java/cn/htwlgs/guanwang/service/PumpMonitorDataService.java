package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.PumpMonitorDataDto;
import cn.htwlgs.guanwang.entity.PumpMonitorData;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.PumpMonitorDataRepository;
import cn.htwlgs.guanwang.utils.EasyExcelListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class PumpMonitorDataService {

    @Autowired
    private PumpMonitorDataRepository repository;

    public void save(PumpMonitorDataDto dto) {
        repository.save(new PumpMonitorData(dto));
    }

    public void saveAndFlush(PumpMonitorDataDto dto) {
        Optional<PumpMonitorData> data = repository.findById(dto.getId());
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

    public void importExcel(MultipartFile file) throws IOException {
        AnalysisEventListener<PumpMonitorDataDto> userAnalysisEventListener = EasyExcelListener.getListener(this.batchInsertMonitorData(),50);
        EasyExcel.read(file.getInputStream(), PumpMonitorDataDto.class, userAnalysisEventListener).sheet().doRead();
    }
    public Consumer<List<PumpMonitorDataDto>> batchInsertMonitorData(){
        return list -> saveMonitorData(list);
    }

    private void saveMonitorData(List<PumpMonitorDataDto> list) {
        List<PumpMonitorData> dataList = new ArrayList<>();
        list.forEach(item->{
            dataList.add(new PumpMonitorData(item));
        });
        repository.saveAll(dataList);
    }
}