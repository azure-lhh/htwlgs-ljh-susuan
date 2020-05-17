package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.AttachmentDto;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.enums.AttachmentTypeEnum;
import cn.htwlgs.guanwang.entity.Attachment;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.AttachmentRepository;
import cn.htwlgs.guanwang.utils.UserUtils;
import com.alibaba.excel.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName AttachmentService
 * @Description
 * @Author lihouhai
 * @Date 2020/5/6 10:34
 * @Version 1.0
 */
@Slf4j
@Service
public class AttachmentService {
    @Autowired
    private AttachmentRepository repository;


    public void create(AttachmentDto dto){
        Attachment attachment = new Attachment();
        BeanUtils.copyProperties(dto,attachment);
        attachment.setEngType(AttachmentTypeEnum.getEnumOrdinal(dto.getEngType()));
        attachment.setDeleted(Long.valueOf(0));
        attachment.setCreateTime(LocalDateTime.now());
        attachment.setCreateUser(UserUtils.getUserName());
        repository.save(attachment);
    }

    public int deleteByIdIn(List<String> idList){
        List<Integer> ids = new ArrayList<>();
        idList.forEach(item->{
            ids.add(Integer.parseInt(item));
        });
        return  repository.deleteAllByIdIn(ids);
    }

    public void update(AttachmentDto dto){
        Optional<Attachment> data = repository.findById(dto.getId());
        if(!data.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        CopyUtils.copyProperties(dto,data.get());
        data.get().setEngType(AttachmentTypeEnum.getEnumOrdinal(dto.getEngType()));
        repository.saveAndFlush(data.get());
    }

    public List<AttachmentDto> findAllByEngTypeAndEngId(Integer engId,Integer engType){
        AttachmentTypeEnum enumOrdinal = AttachmentTypeEnum.getEnumOrdinal(engType);
        List<Attachment> all = repository.findAllByEngTypeAndEngId(enumOrdinal,engId);
        if (CollectionUtils.isEmpty(all)){
            return new ArrayList();
        }
        List<AttachmentDto> list = new ArrayList();
        all.forEach(c ->{
            AttachmentDto dto = new AttachmentDto();
            BeanUtils.copyProperties(c,dto);
            dto.setEngType(engType);
            list.add(dto);
        });
        return list;
    }
}
