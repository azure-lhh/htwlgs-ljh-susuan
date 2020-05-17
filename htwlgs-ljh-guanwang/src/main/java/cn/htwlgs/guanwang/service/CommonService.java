package cn.htwlgs.guanwang.service;

import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.DataDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.TypeNameDto;
import cn.htwlgs.guanwang.dtos.enums.GWLevelEnum;
import cn.htwlgs.guanwang.entity.SewagePart;
import cn.htwlgs.common.exception.BusinessException;
import com.alibaba.excel.util.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CommonService {


    public List<StatisticsVarCherDto> getSetupTimeList(List<LocalDate> dates) {
        List<StatisticsVarCherDto> list = new ArrayList<>();
        StatisticsVarCherDto a = new StatisticsVarCherDto();
        StatisticsVarCherDto b = new StatisticsVarCherDto();
        StatisticsVarCherDto c = new StatisticsVarCherDto();
        StatisticsVarCherDto d = new StatisticsVarCherDto();
        StatisticsVarCherDto e = new StatisticsVarCherDto();
        StatisticsVarCherDto f = new StatisticsVarCherDto();
        a.setName(Constants.STATISTICS_YEAR_A);
        b.setName(Constants.STATISTICS_YEAR_B);
        c.setName(Constants.STATISTICS_YEAR_C);
        d.setName(Constants.STATISTICS_YEAR_D);
        e.setName(Constants.STATISTICS_YEAR_E);
        f.setName(Constants.STATISTICS_YEAR_F);
        dates.forEach(item->{
            int year = item.until(LocalDate.now()).getYears();
            if(year < 2){
                a.setNum(a.getNum().add(BigDecimal.valueOf(1)));
            } else if(year < 3){
                b.setNum(b.getNum().add(BigDecimal.valueOf(1)));
            } else if(year < 6){
                c.setNum(c.getNum().add(BigDecimal.valueOf(1)));
            } else if(year < 10){
                d.setNum(d.getNum().add(BigDecimal.valueOf(1)));
            } else if(year < 20){
                e.setNum(e.getNum().add(BigDecimal.valueOf(1)));
            }else{
                f.setNum(f.getNum().add(BigDecimal.valueOf(1)));
            }
        });
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.add(f);
        return list;
    }

    public List<StatisticsVarCherDto> getList(List<LocalDateTime> dates) {
        List<StatisticsVarCherDto> list = new ArrayList<>();
        int[] num = {2,3,6,10,20,20};
        int begin = 1;
        //Iterator<LocalDate> iterator ;
        for (int i = 0; i < num.length; i++) {
            StatisticsVarCherDto dto = new StatisticsVarCherDto();
            //iterator = dates.iterator();
            //while (iterator.hasNext()) {
                //LocalDate item = iterator.next();
            if(dates == null || dates.size() < 1){
                if (i == num.length - 1) {
                    dto.setName(num[i] + "年以上");
                }else{
                    dto.setName(begin + "-" + num[i] + "年");
                }
            }
            for (LocalDateTime item : dates) {
                //Duration duration = Duration.between(LocalDateTime.now(), item);
                //int year = duration。getYear();
                int year = item.toLocalDate().until(LocalDate.now()).getYears();
                if (i == num.length - 1) {
                    dto.setName(num[i] + "年以上");
                    if(year > num[i]){
                        dto.setNum(dto.getNum().add(BigDecimal.valueOf(1)));
                    }
                } else {
                    dto.setName(begin + "-" + num[i] + "年");
                    if (year < num[i]) {
                        if(begin == 1 || year>= begin) {
                            dto.setNum(dto.getNum().add(BigDecimal.valueOf(1)));
                            //iterator.remove();
                        }
                    }
                }
            }
            list.add(dto);
            begin = num[i];
        }
        return list;
    }


    public List<DataDto> getPartRadiis(List<SewagePart> partRadiis) {

        List<DataDto> dataDtos = new ArrayList<>();
        DataDto<String> dtox = new DataDto();
        DataDto<Integer> dtoy1 = new DataDto();
        DataDto<Integer> dtoy2= new DataDto();
        ArrayList<String> listx = new ArrayList();
        ArrayList<Integer> listy1 = new ArrayList();
        ArrayList<Integer> listy2 = new ArrayList();

        double[] num = {0.0,10.0,20.0,50.0,100.0,200};

        for (int i = 0; i < num.length; i++) {
            Integer yw = 0;
            Integer yo= 0;
            String  name = "";
            for (SewagePart part:partRadiis) {
                double outSide = part.getOutside().doubleValue();
                double within = part.getWithin().doubleValue();
                if (i  ==  num.length -1){
                    name = num[i] + "以上";
                    if (outSide >= num[i]){
                        yo++;
                    }
                    if (within >= num[i]){
                        yw++;
                    }
                }else  {
                    name = num[i] + "-" + num[i+1];
                    if (outSide < num[i+1] && outSide >= num[i]){
                        yo++;
                    }
                    if (within < num[i+1] && within >= num[i]){
                        yw++;
                    }
                }
            }
            listx.add(name);
            listy1.add(yw);
            listy2.add(yo);
        }
        dtox.setName("半径");
        dtoy1.setName("内径数量");
        dtoy2.setName("外径数量");
        dtox.setList(listx);
        dtoy1.setList(listy1);
        dtoy2.setList(listy2);
        dataDtos.add(dtox);
        dataDtos.add(dtoy1);
        dataDtos.add(dtoy2);
        return dataDtos;
    }

    public List<StatisticsVarCherDto> getLeaveListByGWLevelEnum(List<StatisticsVarCherDto> list) {
        List<StatisticsVarCherDto> levelList = new ArrayList<>();
        Iterator<StatisticsVarCherDto> iterator ;
        for(GWLevelEnum levelEnum:GWLevelEnum.values()) {
            StatisticsVarCherDto dto = new StatisticsVarCherDto();
            dto.setName(levelEnum.getVal());
            iterator = list.iterator();
            while (iterator.hasNext()) {
                StatisticsVarCherDto item = iterator.next();
                if (levelEnum.ordinal() == Integer.parseInt(item.getName())) {
                    dto.setNum(item.getNum());
                    iterator.remove();
                }
            }
            levelList.add(dto);
        }
        return levelList;
    }

    /**
     * @Description:  判断管段是否重复
     *
     * @param allByPartNumIn  所有管段
     * @return: void
     *
     */
    public void isgGWPartNum(List<Map<String, Object>>  allByPartNumIn) {
        List<TypeNameDto> allByPartNum = JSONArray.parseArray(JSON.toJSONString(allByPartNumIn), TypeNameDto.class);
        if (!CollectionUtils.isEmpty(allByPartNum)){
            List<String> names = new ArrayList<>();
            for (TypeNameDto dto:allByPartNum) {
                names.add(dto.getName());
            }
            log.error("重复管段编号:{}", JSON.toJSONString(names));
            throw new BusinessException("重复管段编号:"+ JSON.toJSONString(names));
        }
    }

}
