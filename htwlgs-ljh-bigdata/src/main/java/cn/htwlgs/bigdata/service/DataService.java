package cn.htwlgs.bigdata.service;

import cn.htwlgs.bigdata.dtos.*;
import cn.htwlgs.bigdata.dtos.enums.RiverTypeEnum;
import cn.htwlgs.bigdata.entity.WaterMonitor;
import cn.htwlgs.bigdata.exception.BusinessException;
import cn.htwlgs.bigdata.repository.WaterMonitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName DataService
 * @Description
 * @Author lihouhai
 * @Date 2020/5/11 16:50
 * @Version 1.0
 */
@Service
public class DataService {
    @Autowired
    WaterMonitorRepository waterMonitorRepository;
    /**
     * 断面水质评价
     */
    public ResultDto findSecTionWaTerEvaluate(DataDto dataDto){
            RiverTypeEnum typeOrdinal = RiverTypeEnum.getTypeOrdinal(dataDto.getType());
            List<MonitorDto> aqi = WaterMonitorCompartor.waterCount(typeOrdinal,dataDto);
            if (aqi.size() == 0){
                throw  new BusinessException("不能全部属性为空");
            }
            ResultDto rDto =  getSecTionWaTerPriority(aqi);
            rDto.setSerialNo(dataDto.getSerialNo());
//            int level = WaterMonitorCompartor.sectionEvaluate(rDto.getLevel());
            WaterMonitor build = WaterMonitor.builder().keyPollutant(rDto.getKeyPollutant()).level(rDto.getLevel()).wTemp(dataDto.getWTemp()).ph(dataDto.getPh())
                    .num(rDto.getNum()).type(dataDto.getType()).serialNo(rDto.getSerialNo()).basin(dataDto.getBasin()).build();
            waterMonitorRepository.save(build);
            return  rDto.toBuilder().level(rDto.getLevel()).build();

    }
    /**
     * 湖库水质-多站点评价
     * 断面
     */
    public ResultDto  getSecTionWaTerPriority(List<MonitorDto>   list){
//        list.forEach(c ->{
//            int v = WaterMonitorCompartor.sectionEvaluate(c.getLevel());
//            c.setLevel();
//        });
        List<MonitorDto> collect = list.stream()
                .filter(c -> c.isFlag()).collect(Collectors.toList());
         for (MonitorDto m:collect) {
             if (m.getIndex() == 0){
                 m.setSprstd(0.00);
             }else {
                 double  v = WaterMonitorCompartor.CmpSpstandardMultiple(m);
                 m.setSprstd(v);
             }

         }

        List<MonitorDto> collect1 = collect.stream().sorted(
                Comparator.comparing(MonitorDto::getLevel).reversed()
                        .thenComparing(MonitorDto::getSprstd,Comparator.reverseOrder())
                        .thenComparing(MonitorDto::getNum,Comparator.reverseOrder()))
                .collect(Collectors.toList());
         return WaterMonitorCompartor.sectionPrimaryPollutant(collect1);
    }
    /**
     *  河流、流域（水系）水质评价
     */
    public SectionResultDto findRiverWaTerEvaluate(List<DataDto> list){
        if (list !=null){
            if (list.size()  <= 5 ){//断面树小于5
                return  secTionNumGreaterLess5(list);
            }else {
                return   secTionNumGreaterThan5(list);
            }
        }
        return null;
    }

    /**
     *  断面数小于5
     */
    public SectionResultDto  secTionNumGreaterLess5(List<DataDto> list){
        DataDto dataDto = WaterMonitorCompartor.secTionNumLessThan5(list);
        ResultDto resultDto = findSecTionWaTerEvaluate(dataDto);
        List<SerialNoMonitorDto>  list1 = new ArrayList<>();
        int key = -1;
            for (DataDto dto:list) {
                key++;
                ResultDto td1 = findSecTionWaTerEvaluate(dto);
                SerialNoMonitorDto serialNoMonitorDto = new SerialNoMonitorDto(td1,dto.getSerialNo());
                list1.add(serialNoMonitorDto);
            }
        return  SectionResultDto.builder().level(resultDto.getLevel()).keyPollutant(resultDto.getKeyPollutant()).num(resultDto.getNum()).list(list1).build();
    }
    /**
     *  断面数大于5
     */
    public SectionResultDto  secTionNumGreaterThan5(List<DataDto> list){
        List<ResultDto>  dtos = new ArrayList<>();
        for (DataDto  DataDto:list) {
            ResultDto evaluate = findSecTionWaTerEvaluate(DataDto);
            dtos.add(evaluate);
        }
        TreeMap<Integer, List<ResultDto>> collect2 = dtos.stream().collect(Collectors.groupingBy(ResultDto::getLevel, TreeMap::new, Collectors.toList()));

        Integer key = null;
        Integer num13 = 0;
        Integer num5 = 0;
        Iterator iter = collect2.entrySet().iterator();
        List <MonitorDto> monitorList = null;
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            // 获取key
            monitorList =  (List<MonitorDto>)entry.getValue();
            Integer num =monitorList.size();
            key = (Integer)entry.getKey();
            if (key==1 || key == 3){
                num13 +=num;
            }
            if (key == 6){
                num5 +=num;
            }
        }
        double idx13 = new BigDecimal((float)num13/dtos.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double idx6 = new BigDecimal((float)num5/dtos.size()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        //水质评价比率
        int i = WaterMonitorCompartor.waterSystemEvaluate(idx13, idx6);
        //获取主要污染物指标
        return getKeyPollutant(dtos);
    }

    public SectionResultDto getKeyPollutant(List<ResultDto>  dtos){
        TreeMap<String, List<ResultDto>> collect2 = dtos.stream().collect(Collectors.groupingBy(ResultDto::getKeyPollutant, TreeMap::new, Collectors.toList()));
        Iterator iter = collect2.entrySet().iterator();
        List <MonitorDto> monitorList = new ArrayList<MonitorDto>();
        int size = dtos.size();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            // 获取key
            List <ResultDto> vList =  (List<ResultDto>)entry.getValue();
            String key = (String)entry.getKey();
            double v = WaterMonitorCompartor.CmpSectionUnqualifiedRate(vList.size(), size);
            MonitorDto build = MonitorDto.builder().name(key).num(v).level(vList.get(0).getLevel()).build();
            monitorList.add(build);
        }
        List<MonitorDto> collect = monitorList.stream().sorted(Comparator.comparing(MonitorDto::getNum).reversed()).collect(Collectors.toList());
        List<SerialNoMonitorDto>  list1 = new ArrayList<>();
        for (MonitorDto dto:collect) {
            SerialNoMonitorDto serialNoMonitorDto = new SerialNoMonitorDto(dto,null);
            list1.add(serialNoMonitorDto);
        }
        ResultDto resultDto = WaterMonitorCompartor.basinPrimaryPollutant(collect);
        return  SectionResultDto.builder().list(list1).level(resultDto.getLevel()).keyPollutant(resultDto.getKeyPollutant()).num(resultDto.getNum()).build();
    }

    /**
     * 湖库水质评价——多次监测
     */
    public ResultDto  findMultiMonitorEvaluate(List<DataDto> list){
        Set<String> set = new HashSet<>();
        for (DataDto dto:list) {
            set.add(dto.getSerialNo());
        }
        List<DataDto> DataDtos = new ArrayList<>();
        for (String key:set) {
            List<DataDto> collect = list.stream().filter(c -> key.equals(c.getSerialNo())).collect(Collectors.toList());
            DataDto dto =  WaterMonitorCompartor.secTionNumLessThan5(collect);
            DataDtos.add(dto);
        }
        DataDto dto =  WaterMonitorCompartor.secTionNumLessThan5(DataDtos);
        ResultDto secTionWaTerEvaluate = findSecTionWaTerEvaluate(dto);
        return secTionWaTerEvaluate;
    }
    /**
     * 湖库水质-多站点评价
     */
    public ResultDto findLakeWaTerMultiSiteEvaluate(List<DataDto> list){
        DataDto DataDto = WaterMonitorCompartor.secTionNumLessThan5(list);
        return   findSecTionWaTerEvaluate(DataDto);
    }


}
