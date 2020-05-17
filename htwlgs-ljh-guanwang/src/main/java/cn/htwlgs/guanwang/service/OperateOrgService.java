package cn.htwlgs.guanwang.service;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.OperateOrgDto;
import cn.htwlgs.guanwang.entity.OperateOrg;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.OperateOrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @ClassName MangeOrgService
 * @Description
 * @Author lihouhai
 * @Date 2020/5/10 15:45
 * @Version 1.0
 */
@Slf4j
@Service
public class OperateOrgService {
    @Autowired
    OperateOrgRepository operateOrgRepository;

    public OperateOrgDto findById(Integer id){
        Optional<OperateOrg> byId = operateOrgRepository.findById(id);
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        OperateOrg org = byId.get();
        OperateOrgDto orgDto = new OperateOrgDto();
        BeanUtils.copyProperties(org,orgDto);
        return orgDto;
    }
}
