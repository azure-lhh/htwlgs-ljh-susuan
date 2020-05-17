package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.dtos.enums.*;
import cn.htwlgs.guanwang.entity.*;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.repository.*;
import cn.htwlgs.guanwang.utils.PageList;
import cn.htwlgs.guanwang.utils.UserUtils;
import com.alibaba.excel.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName EngInfoService
 * @Description 工程信息服务层
 * @Author lihouhai
 * @Date 2020/4/30 13:09
 * @Version 1.0
 */
@Service
public class SubEngInfoService {
    @Autowired
    private EngInfoRepository engInfoRepository;
    @Autowired
    private AttachmentService service;

    @Autowired
    private EngManholeTitleRepository manholeTitleRepository;
    @Autowired
    private EngPipeDetectRepository pipeDetectRepository;
    @Autowired
    private EngPipeEvalRepository pipeEvalRepository;
    @Autowired
    private EngPipeTitleRepository pipeTitleRepository;
    @Autowired
    private EngPipeTitleDetailRepository detailRepository;

    @Autowired
    private EngSewageTitleRepository repository;


    public PageList findAllByPipeCodeAndType(Integer engType, String code, Integer type, int pageNum, int pageSize) {
        //判断type
        EngineeringTypeEnum enumOrdinal = EngineeringTypeEnum.getEnumOrdinal(engType);
        EngTypeEnum typeEnum = EngTypeEnum.getEnumOrdinal(type);
        Page<EngInfo> partPage = seletePagingByParam(enumOrdinal, code, typeEnum, pageNum, pageSize);
        List<EngInfo> list = partPage.getContent();
        if (CollectionUtils.isEmpty(list)) {
            return new PageList<>(null, pageNum, pageSize, repository.count());
        }
        List<EngInfoDto> dtos = new ArrayList<>();
        list.forEach(c -> {
            EngInfoDto detect = new EngInfoDto();
            BeanUtils.copyProperties(c, detect);
            detect.setType(c.getType().ordinal());
            detect.setEngType(c.getEngType().ordinal());
            dtos.add(detect);
        });
        return new PageList<>(dtos, pageNum, pageSize, partPage.getTotalElements());
    }

    public Page<EngInfo> seletePagingByParam(EngineeringTypeEnum enumOrdinal, String proPart, EngTypeEnum type, int pageNum, int pageSize) {
        Page<EngInfo> pageList = null;
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "detectTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return engInfoRepository.findAllByEngTypeAndTypeAndProPart(enumOrdinal, type, proPart, pageable);
    }


    public PageList<EngInfoDto> getEngInfoPaging(Integer engType, String code, Integer type, Integer pageNum, Integer pageSize) {
        EngTypeEnum.getEnumOrdinal(type);
        PageList<EngInfoDto> all = findAllByPipeCodeAndType(engType, code, type, pageNum, pageSize);
        return all;
    }


    public int deleteEngDetect(List<String> idList) {
        List<Integer> ids = new ArrayList<>();
        idList.forEach(item -> {
            ids.add(Integer.parseInt(item));
        });
        int count = engInfoRepository.deleteAllByIdIn(ids);
        return count;
    }

    public void createSubEngDetect(EngInfoDto dto) {

        EngInfo detect = new EngInfo();
        BeanUtils.copyProperties(dto, detect);
        detect.setType(EngTypeEnum.getEnumOrdinal(dto.getType()));
        detect.setEngType(EngineeringTypeEnum.getEnumOrdinal(dto.getEngType()));
        detect.setCreateUser(UserUtils.getUserName());
        engInfoRepository.save(detect);
    }


    public void updateSubEngDetect(EngInfoDto dto) {
        Optional<EngInfo> byId = engInfoRepository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
//        EngInfo detect = new EngInfo();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setType(EngTypeEnum.getEnumOrdinal(dto.getType()));
        byId.get().setUpdateTime(LocalDateTime.now());
        byId.get().setUpdateUser(UserUtils.getUserName());
        byId.get().setEngType(EngineeringTypeEnum.getEnumOrdinal(dto.getEngType()));
        engInfoRepository.saveAndFlush(byId.get());
    }

    public SubEngInfoDto querySubInfo(String ids, Integer typeEnum) {
        SubEngInfoDto dto = new SubEngInfoDto();
        Integer id = Integer.valueOf(ids);
        if (EngTypeEnum.PART.ordinal() == typeEnum) {
            EngPipeInfoDto all = findPipeEngInfolById(id);
            dto.setPipeInfo(all);
        } else if (EngTypeEnum.MANHOLE.ordinal() == typeEnum) {
            EngManholeTitleDto manholeEngInfoById = findManholeEngInfoById(id);
            dto.setManholeInfo(manholeEngInfoById);
        } else if (EngTypeEnum.SEWAG_OUTLET.ordinal() == typeEnum) {
            EngSewageTitleDto manholeEngInfoById = findSewageEngInfoById(id);
            dto.setSewageInfo(manholeEngInfoById);
        } else {
            throw new BusinessException("未找到对应typeEnum类型数据");
        }
        dto.setId(ids);
        dto.setTypeEnum(EngTypeEnum.getEnumOrdinal(typeEnum).getVal());
        List<AttachmentDto> attachmentDtos = service.findAllByEngTypeAndEngId(id, AttachmentTypeEnum.ENG.ordinal());
        dto.setAttachment(attachmentDtos);
        return dto;
    }


    public EngPipeInfoDto findPipeEngInfolById(Integer engId) {

        EngPipeTitle engPipeTitle = pipeTitleRepository.findByEngId(engId);//检测单
        if (engPipeTitle == null) {
            engPipeTitle = new EngPipeTitle();
        }
        EngPipeTitleDto engPipeTitleDto = new EngPipeTitleDto();
        CopyUtils.copyProperties(engPipeTitle, engPipeTitleDto);
        List<EngPipeTitleDetail> listDetails = detailRepository.findByEngId(engId);
        List<EngPipeTitleDetailDto> dtos = new ArrayList<>();
        listDetails.forEach(c -> {
            EngPipeTitleDetailDto detect = new EngPipeTitleDetailDto();
            BeanUtils.copyProperties(c, detect);
            dtos.add(detect);
        });
        engPipeTitleDto.setDetails(dtos);

        EngPipeDetect detectList = pipeDetectRepository.findByEngId(engId);//管段缺陷详情
        if (detectList == null) {
            detectList = new EngPipeDetect();
        }
        EngPipeDetectDto detect = new EngPipeDetectDto();
        CopyUtils.copyProperties(detectList, detect);
        EngPipeEval engPipeEval = pipeEvalRepository.findByEngId(engId);//缺陷评估
        if (engPipeEval == null) {
            engPipeEval = new EngPipeEval();
        }
        EngPipeEvalDto pipeEval = new EngPipeEvalDto();
        CopyUtils.copyProperties(engPipeEval, pipeEval);
        EngPipeInfoDto engPipeInfoDto = new EngPipeInfoDto(engPipeTitleDto, detect, pipeEval);
        return engPipeInfoDto;
    }

    public EngManholeTitleDto findManholeEngInfoById(Integer id) {
        EngManholeTitle byId = manholeTitleRepository.findByEngId(id);
        if (byId == null) {
            return null;
        }
        EngManholeTitleDto dto = new EngManholeTitleDto();
        CopyUtils.copyProperties(byId, dto);
        dto.setMheShape(byId.getMheShape().getVal());
        return dto;
    }

    public EngSewageTitleDto findSewageEngInfoById(Integer id) {
        EngSewageTitle byId = repository.findByEngId(id);
        if (byId == null) {
            return null;
        }
        EngSewageTitleDto dto = new EngSewageTitleDto();
        BeanUtils.copyProperties(byId, dto);
        dto.setOutfallType(byId.getOutfallType().getVal());
        return dto;
    }


    public void createPipeCheckList(EngPipeTitleDto dto) {


        EngPipeTitle detect = new EngPipeTitle();
        BeanUtils.copyProperties(dto, detect);
        detect.setCreateUser(UserUtils.getUserName());
        detect.setDeleted(Long.valueOf(0));
        detect.setCreateTime(LocalDateTime.now());
        detect.setCreateUser(UserUtils.getUserName());
        pipeTitleRepository.save(detect);
        List<EngPipeTitleDetailDto> details = dto.getDetails();
        for (EngPipeTitleDetailDto detail : details) {
            EngPipeTitleDetail pipeDetail = new EngPipeTitleDetail();
            CopyUtils.copyProperties(detail, pipeDetail);
            pipeDetail.setEngId(dto.getEngId());
            detailRepository.save(pipeDetail);

        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePipeChecklist(String id,String engId) {
        pipeTitleRepository.deleteById(Integer.valueOf(id));
        detailRepository.deleteByEngId(Integer.valueOf(engId));

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSubEngDetect(EngPipeTitleDto dto) {
        Optional<EngPipeTitle> byId = pipeTitleRepository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
        detailRepository.deleteByEngId(dto.getEngId());//编辑先删除旧数据，再保存新数据
//        EngPipeTitle detect = new EngPipeTitle();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setUpdateTime(LocalDateTime.now());
        byId.get().setUpdateUser(UserUtils.getUserName());
        pipeTitleRepository.saveAndFlush(byId.get());
        List<EngPipeTitleDetailDto> details = dto.getDetails();
        for (EngPipeTitleDetailDto detail : details) {
            EngPipeTitleDetail pipeDetail = new EngPipeTitleDetail();
            CopyUtils.copyProperties(detail, pipeDetail);
            pipeDetail.setEngId(dto.getEngId());
            detailRepository.save(pipeDetail);
        }
    }


    public void createSewageTitle(EngSewageTitleDto dto) {
        EngSewageTitle detect = new EngSewageTitle();
        BeanUtils.copyProperties(dto, detect);
        detect.setUpdateUser("测试");
        detect.setCreateUser("测试1");
        detect.setDetectUser("测试11");
        detect.setOutfallType(PFTypeEnum.getEnumData(dto.getOutfallType()));
        repository.save(detect);
    }

    public void updateSewageTitle(EngSewageTitleDto dto) {
        Optional<EngSewageTitle> byId = repository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
//        EngSewageTitle detect = new EngSewageTitle();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setOutfallType(PFTypeEnum.getEnumData(dto.getOutfallType()));
        byId.get().setUpdateUser(UserUtils.getUserName());
        repository.saveAndFlush(byId.get());
    }

    public void deleteSewageTitle(String id) {
        repository.deleteById(Integer.valueOf(id));
    }

    public void createManholeTitle(EngManholeTitleDto dto) {
        EngManholeTitle detect = new EngManholeTitle();
        BeanUtils.copyProperties(dto, detect);
        detect.setMheShape(ShapeTypeEnum.getEnumData(dto.getMheShape()));
        detect.setCreateUser(UserUtils.getUserName());
       /* detect.setDetectUser("测试11");
        detect.setVerifyUser("测试12");*/
        manholeTitleRepository.save(detect);
    }


    public void updateManholeTitle(EngManholeTitleDto dto) {
        Optional<EngManholeTitle> byId = manholeTitleRepository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }

//        EngManholeTitle detect = new EngManholeTitle();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setMheShape(ShapeTypeEnum.getEnumData(dto.getMheShape()));
        byId.get().setUpdateUser(UserUtils.getUserName());
        manholeTitleRepository.saveAndFlush(byId.get());
    }

    public void deleteManholeTitle(String id) {
        manholeTitleRepository.deleteById(Integer.valueOf(id));

    }


    public void createPipeDetect(EngPipeDetectDto dto) {
        EngPipeDetect detect = new EngPipeDetect();
        BeanUtils.copyProperties(dto, detect);
        detect.setCreateUser(UserUtils.getUserName());
        detect.setDeleted(Long.valueOf(0));
        detect.setCreateTime(LocalDateTime.now());
        detect.setUpdateTime(LocalDateTime.now());
        pipeDetectRepository.save(detect);
    }


    public void deletePipeDetect(String id) {
        pipeDetectRepository.deleteById(Integer.valueOf(id));

    }

    public void updatePipeDetect(EngPipeDetectDto dto) {
        Optional<EngPipeDetect> byId = pipeDetectRepository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }

//        EngPipeDetect detect = new EngPipeDetect();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setUpdateTime(LocalDateTime.now());
        byId.get().setUpdateUser(UserUtils.getUserName());
        pipeDetectRepository.saveAndFlush(byId.get());
    }


    public void createPipeEval(EngPipeEvalDto dto) {
        EngPipeEval detect = new EngPipeEval();
        BeanUtils.copyProperties(dto, detect);
        detect.setCreateUser(UserUtils.getUserName());
        detect.setDeleted(Long.valueOf(0));
        detect.setCreateTime(LocalDateTime.now());
        detect.setUpdateTime(LocalDateTime.now());
        pipeEvalRepository.save(detect);
    }


    public void deletePipeEval(String id) {
        pipeEvalRepository.deleteById(Integer.valueOf(id));

    }

    public void updatePipeEval(EngPipeEvalDto dto) {
        Optional<EngPipeEval> byId = pipeEvalRepository.findById(dto.getId());
        if (!byId.isPresent()) {
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }

//        EngPipeEval detect = new EngPipeEval();
        CopyUtils.copyProperties(dto, byId.get());
        byId.get().setUpdateTime(LocalDateTime.now());
        byId.get().setUpdateUser(UserUtils.getUserName());
        pipeEvalRepository.saveAndFlush(byId.get());
    }


}
