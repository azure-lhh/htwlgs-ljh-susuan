package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.EngInfoHzDto;
import cn.htwlgs.guanwang.dtos.enums.EngTypeEnum;
import cn.htwlgs.guanwang.dtos.enums.EngineeringTypeEnum;
import cn.htwlgs.guanwang.entity.EngInfoHz;
import cn.htwlgs.guanwang.repository.EngInfoHzRepository;
import cn.htwlgs.guanwang.utils.EasyExcelUtil;
import cn.htwlgs.guanwang.utils.PageList;
import cn.htwlgs.guanwang.utils.UserUtils;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import cn.htwlgs.common.utils.CopyUtils;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName EngInfoService
 * @Description 工程信息服务层
 * @Author lihouhai
 * @Date 2020/4/30 13:09
 * @Version 1.0
 */
@Service
public class EngInfoService {



    @Autowired
    private EngInfoHzRepository engInfoHzRepository;



    public PageList getEngList(int engType,String keyWord,String areaCode,int pageNum,int pageSize){
        EngineeringTypeEnum enumOrdinal = EngineeringTypeEnum.getEnumOrdinal(engType);
        Page<EngInfoHz> partPage = seletePagingByParam( enumOrdinal,keyWord, areaCode,pageNum, pageSize);
        List<EngInfoHz> list = partPage.getContent();
        if(CollectionUtils.isEmpty(list)){
            return new PageList<>(null,pageNum,pageSize,engInfoHzRepository.count());
        }
        List<EngInfoHzDto> dtos = new ArrayList<>();
        list.forEach( c -> {
            EngInfoHzDto detect = new EngInfoHzDto();
            CopyUtils.copyProperties(c,detect);
            detect.setType(c.getType().ordinal());
            detect.setEngType(c.getEngType().ordinal());
            dtos.add(detect);
        });
        return new PageList<>(dtos,pageNum,pageSize,partPage.getTotalElements());
    }

    public Page<EngInfoHz> seletePagingByParam(EngineeringTypeEnum enumOrdinal, String keyWord, String areaCode, int pageNum, int pageSize){
        //获取PageRequest对象 index:页码 从0开始  size每页容量 sort排序方式 "id"->properties 以谁为准排序
        Sort sort = Sort.by(Sort.Direction.DESC, "detectTime");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,sort);
        if (StringUtils.hasLength(keyWord)){
            return engInfoHzRepository.findAllByEngTypePipeNameLike(enumOrdinal,"%"+keyWord+"%", pageable);
        }
        if (StringUtils.hasLength(areaCode)) {
            return engInfoHzRepository.findAllByEngTypeAndAreaCode(enumOrdinal,areaCode, pageable);
        }
        return engInfoHzRepository.findAllByEngType(enumOrdinal, pageable);
    }


    public  void insertEng(EngInfoHzDto engInfoDto){
        EngInfoHz engDetect = new EngInfoHz();
        CopyUtils.copyProperties(engInfoDto,engDetect);
        engDetect.setCreateTime(LocalDateTime.now());
        engDetect.setCreateUser(UserUtils.getUserName()); // 创建人暂时无法获取
        engDetect.setDeleted(Long.valueOf(0));
        engDetect.setType(EngTypeEnum.getEnumOrdinal(engInfoDto.getType()));
        engDetect.setEngType(EngineeringTypeEnum.getEnumOrdinal(engInfoDto.getEngType()));
        engInfoHzRepository.save(engDetect);
    }

    public int deleteEng(List<String> idList){
        List<Integer> ids = new ArrayList<>();
        idList.forEach(item->{
            ids.add(Integer.parseInt(item));
        });
        int count = engInfoHzRepository.deleteAllByIdIn(ids);
        return count;
    }

    public void updateEngInfoHz(EngInfoHzDto dto){
        Optional<EngInfoHz> byId = engInfoHzRepository.findById(dto.getId());
        if(!byId.isPresent()){
            throw new BusinessException(Constants.UPDATE_EXIST_ERROR);
        }
//        EngInfoHz detect = new EngInfoHz();
        CopyUtils.copyProperties(dto,byId.get());
        byId.get().setUpdateUser(UserUtils.getUserName());
        byId.get().setType(EngTypeEnum.getEnumOrdinal(dto.getType()));
        byId.get().setEngType(EngineeringTypeEnum.getEnumOrdinal(dto.getEngType()));
        byId.get().setUpdateTime(LocalDateTime.now());
        engInfoHzRepository.saveAndFlush(byId.get());
    }


    public List<EngInfoHz> findByIdList(List<Integer> idList){
        List<EngInfoHz> all = engInfoHzRepository.findByIdIn(idList);
        if(CollectionUtils.isEmpty(all)){
            return null;
        }
        return all;
    }


    public void exportExcel(HttpServletResponse response, String fileName, String sheetName, Class<?> procls, ExcelTypeEnum typeEnum, List<Integer> idList) throws IOException {
        if (org.springframework.util.CollectionUtils.isEmpty(idList)) {
            throw new BusinessException("未选中导出数据");
        }
        List<EngInfoHz> all = this.findByIdList(idList);
        List<EngInfoHzDto> list = new ArrayList<>();
        all.forEach( c -> {
            EngInfoHzDto engInfoHzDto = new EngInfoHzDto();
            BeanUtils.copyProperties(c,engInfoHzDto);
            list.add(engInfoHzDto);
        });
        // 根据用户传入字段 假设我们要忽略 date
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("deleted");
        EasyExcelUtil.exportExcel(response, list, fileName, sheetName, procls, typeEnum);
    }


}
