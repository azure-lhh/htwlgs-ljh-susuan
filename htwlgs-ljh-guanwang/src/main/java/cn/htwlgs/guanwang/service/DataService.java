package cn.htwlgs.guanwang.service;

import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.guanwang.configuration.ListenerConfig;
import cn.htwlgs.guanwang.dtos.AdminRegionDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.repository.SewagePartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DataService
 * @Description
 * @Author lihouhai
 * @Date 2020/4/24 15:54
 * @Version 1.0
 */
@Slf4j
@Service
public class DataService {

    @Autowired
    SewagePartRepository sewagePartRepository;

    public  List<StatisticsVarCherDto> groupByState(List<StatisticsVarCherDto> byList){
        if (CollectionUtils.isEmpty(byList)){
                throw  new BusinessException("区域数据为空");
        }
        int size =  byList.size();
        List<StatisticsVarCherDto>  list = new ArrayList<>();
        if (size < 5){
            return nameToAreaCodeCollect(byList,list);
        }
        if (size >= 5){
            List<StatisticsVarCherDto> collect = byList.stream().limit(5).collect(Collectors.toList());
            list = nameToAreaCodeCollect(collect,list);
            BigDecimal num = new BigDecimal(0);
            for (int i = 5 ; i < size; i++) {
                BigDecimal num1 = byList.get(i).getNum();
                num= num.add(num1);
            }
            StatisticsVarCherDto dtoOther = new StatisticsVarCherDto("其他合计",num);
            list.add(dtoOther);
        }
        return list;
    }

    public List<StatisticsVarCherDto> nameToAreaCodeCollect(List<StatisticsVarCherDto> collect, List<StatisticsVarCherDto>  list){
        for (StatisticsVarCherDto dto:collect) {
            String areaCode = getAreaCode(dto.getName());
            dto.setName(areaCode);
            list.add(dto);
        }
        return list;
    }


    public String getAreaCode(String code){
        for (AdminRegionDto c:ListenerConfig.adminDtos) {
           String areaCode= String.valueOf(c.getBM());
            if (code.equals(areaCode)){
                return c.getMC();
            }
        }
        //临时返回code 方便调试
        return code;
//        log.error("数据库行政区域未匹配成功");
//        throw  new BusinessException("行政区域匹配不一致");
    }

    public List<String> findAllPipeNum(){
        List<String> names = sewagePartRepository.findAllPipeNum();
        return names;
    }




}
