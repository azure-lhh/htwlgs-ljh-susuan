package cn.htwlgs.bigdata.service;

import cn.htwlgs.bigdata.dtos.DataDto;
import cn.htwlgs.bigdata.dtos.MonitorDto;
import cn.htwlgs.bigdata.dtos.ResultDto;
import cn.htwlgs.bigdata.dtos.enums.RiverTypeEnum;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: htwlgs-ljh-bigdata
 * packageName:  cn.htwlgs.bigdata.utils.aqi
 * @description:  水环境值计算
 * @author: azure
 * @create: 2020/5/11 23:30
 */
public class WaterMonitorCompartor {
    // 质量分指数及对应的污染物项目浓度限值
    public  static  double[][] aqiArr = {
//            { 0, 50, 100, 150, 200 },//水温
//            { 0, 35, 75, 115, 150 },//ph值
            { 7.5, 6, 5, 3, 2 },//溶解氧DO
            { 2, 4, 6, 10, 15},//高锰酸钾指数CODMn
            { 15, 15,20 ,30 ,40 },//CODCr
            {3,3,4,6,10},//BOD5
            {0.15,0.5,1.0,1.5,2.0},//氨氮NH3-N
            {0.02,0.1,0.2,0.3,0.4},//总磷
            {0.01,0.025,0.05,0.1,0.2},//总磷湖库
            {0.2,0.5,1.0,1.5,2.0},//总氮
            {0.01,1.0,1.0,1.0,1.0},//铜
            {0.05,1.0,1.0,2.0,2.0},//锌
            {1.0,1.0,1.0,1.5,1.5},//氰化物
            {0.01,0.01,0.01,0.02,0.02},//硒
            {0.05,0.05,0.05,0.1,0.1},//砷
            {0.00005,0.00005,0.0001,0.001,0.001},//汞
            {0.001,0.005,0.005,0.005,0.01},//镉
            {0.01,0.05,0.05,0.05,0.1},//铬
            {0.01,0.01,0.05,0.05,0.1},//铅
            {0.005,0.05,0.2,0.2,0.2},//氯化物
            {0.002,0.002,0.005,0.01,0.1},//挥发酚
            {0.05,0.05,0.05,0.5,1.0},//石油类
            {0.2,0.2,0.2,0.3,0.3},//阴离子表面活性剂
            {0.05,0.1,0.2,0.5,1.0},//硫化物
            {200,2000,10000,20000,40000}//粪大肠菌群
    };
    /**
     * 计算每种污染物项目
     * @param cp 污染物项目P的质量浓度范围
     * @param r  污染物项目P所在数组中的行号
     * @return
     */
    public static int  countPerIaqi1(double cp, int r) {
        double min = aqiArr[r][0];
        int index= aqiArr[r].length-1;
        double max = aqiArr[r][index];
        if (r == 0 ){
            if (cp < max ){
                return 6;
            }
            for (int j = 0; j < aqiArr[0].length; j++) {
                double v = aqiArr[0][j];
                if (cp >= v ) {
                    return j+1;
//                    break;
                }
            }
        }
        if (cp <= min ){
            return 1;
        }else if (cp > max){
            return index+2;
        }
        else {
            // 对每种污染物的bph、bpl、iaqih、iaqil进行赋值
            for (int j = 1; j < aqiArr[0].length; j++) {
                if (cp <= aqiArr[r][j]) {
                    if(max == cp){
                        return j+1;
                    }
                    if (aqiArr[r][j] == aqiArr[r][j+1]){
                        return j+1;
                    }else {
                        return j+1;

                    }
                }
            }
            return 0;
        }
    }

    public static List<MonitorDto> waterCount(RiverTypeEnum typeEnum, DataDto dataDto) {
        MonitorDto dcodMn = getCODMn(dataDto.getCODMn());
        MonitorDto dDo = getDO(dataDto.getDO());
        MonitorDto dcodCr = getCODCr(dataDto.getCODCr());
        MonitorDto dBOD5 = getBOD5(dataDto.getBOD5());
        MonitorDto dNH4N = getNH4N(dataDto.getNH4N());
        MonitorDto dPTotal =  getPTotal(typeEnum, dataDto.getPTotal());
        MonitorDto dNTotal =  getNTotal(dataDto.getNTotal());
        MonitorDto dWCu =  getWCu(dataDto.getWCu());
        MonitorDto dWZn =  getWZn(dataDto.getWZn());
        MonitorDto dF =  getF( dataDto.getF());
        MonitorDto dSe =  getSe(dataDto.getSe());
        MonitorDto dAs =  getAs(dataDto.getAs());
        MonitorDto dWHg =  getWHg(dataDto.getWHg());
        MonitorDto dCd =  getCd(dataDto.getCd());
        MonitorDto dCr6 =  getCr6( dataDto.getCr6());
        MonitorDto dWPb =  getWPb(dataDto.getWPb());
        MonitorDto dCnTotal =  getCnTotal(dataDto.getCnTotal());
        MonitorDto dVPhen =  getVPhen(dataDto.getWPhen());
        MonitorDto dOils =  getOils(dataDto.getOils());
        MonitorDto dAnSAA =  getAnSAA( dataDto.getAnSAA());
        MonitorDto dS =  getS(dataDto.getS());
        MonitorDto dColoOrg =  getColoOrg(dataDto.getColoOrg());
        // 初始化对象数组
        List<MonitorDto> aList = new ArrayList<MonitorDto>();
        if(dcodMn.getLevel() != 0){
            aList.add(dcodMn);
        }
        if(dDo.getLevel() != 0){
            aList.add(dDo);
        }
        if(dcodCr.getLevel() != 0){
            aList.add(dcodCr);
        }
        if(dBOD5.getLevel() != 0){
            aList.add(dBOD5);
        }
        if(dNH4N.getLevel() != 0){
            aList.add(dNH4N);
        }
        if(dPTotal.getLevel() != 0){
            aList.add(dPTotal);
        }
        if(dNTotal.getLevel() != 0){
            aList.add(dNTotal);
        }
        if(dWCu.getLevel() != 0){
            aList.add(dWCu);
        }
        if(dWZn.getLevel() != 0){
            aList.add(dWZn);
        }
        if(dF.getLevel() != 0){
            aList.add(dF);
        }
        if(dSe.getLevel() != 0){
            aList.add(dSe);
        }
        if(dAs.getLevel() != 0){
            aList.add(dAs);
        }
        if(dWHg.getLevel() != 0){
            aList.add(dWHg);
        }
        if(dCd.getLevel() != 0){
            aList.add(dCd);
        }
        if(dCr6.getLevel() != 0){
            aList.add(dCr6);
        }
        if(dWPb.getLevel() != 0){
            aList.add(dWPb);
        }
        if(dCnTotal.getLevel() != 0){
            aList.add(dCnTotal);
        }
        if(dVPhen.getLevel() != 0){
            aList.add(dVPhen);
        }
        if(dOils.getLevel() != 0){
            aList.add(dOils);
        }
        if(dAnSAA.getLevel() != 0){
            aList.add(dAnSAA);
        }
        if(dS.getLevel() != 0){
            aList.add(dS);
        }
        if(dColoOrg.getLevel() != 0){
            aList.add(dColoOrg);
        }
        List<MonitorDto> collect = aList.stream().filter(c -> 0.0 != c.getNum()).collect(Collectors.toList());
        return collect;
    }
    public static MonitorDto getDO(double DO) {
        int level = 0;
        if(DO >= 0){
            level = countPerIaqi1(DO, 0);
        }
        return MonitorDto.builder().index(0).level(level).name("溶解氧").num(DO).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getCODMn(double CODMn) {
        int level = 0;
        if(CODMn >= 0){
            level = countPerIaqi1(CODMn, 1);
        }
        return MonitorDto.builder().index(1).level(level).name("高锰酸盐指数CODMn").num(CODMn).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getCODCr(double CODCr) {
        int level = 0;
        if(CODCr >= 0){
            level = countPerIaqi1(CODCr, 2);
        }
        return MonitorDto.builder().index(2).level(level).name("化学需氧量(CODCr)").num(CODCr).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getBOD5(double BOD5) {
        int level = 0;
        if(BOD5 >= 0){
            level = countPerIaqi1(BOD5, 3);
        }
        return MonitorDto.builder().index(3).level(level).name("五日生化需氧量(BOD5)").num(BOD5).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getNH4N(double NH4N) {
        int level = 0;
        if(NH4N >= 0){
            level = countPerIaqi1(NH4N, 4);
        }
        return MonitorDto.builder().index(4).level(level).name("氨氮NH4N").num(NH4N).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getPTotal(RiverTypeEnum typeEnum,double PTotal) {
        int level = 0;
        int index = 6;
        if(PTotal >= 0){
            if (RiverTypeEnum.RIVER_1 == typeEnum){
                level = countPerIaqi1(PTotal, 6);
            }else {
                index =5;
                level = countPerIaqi1(PTotal, 5);
            }
        }
        return MonitorDto.builder().index(index).level(level).name("总磷PTotal").num(PTotal).flag(true).priority(1).build();
    }
    public static MonitorDto getNTotal(double NTotal) {
        int level = 0;
        if(NTotal >= 0){
            level = countPerIaqi1(NTotal, 7);
        }
        return MonitorDto.builder().index(7).level(level).name("总氮(湖库以N计)NTotal").num(NTotal).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getWCu(double WCu) {
        int level = 0;
        if(WCu >= 0){
            level = countPerIaqi1(WCu, 8);
        }
        return MonitorDto.builder().index(8).level(level).name("铜WCu").num(WCu).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getWZn(double WZn) {
        int level = 0;
        if(WZn >= 0){
            level = countPerIaqi1(WZn, 9);
        }
        return MonitorDto.builder().index(9).level(level).name("锌WZn").num(WZn).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getF(double F) {
        int level = 0;
        if(F >= 0){
            level = countPerIaqi1(F, 10);
        }
        return MonitorDto.builder().index(10).level(level).name("氟化物(以F-计)").num(F).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getSe(double Se) {
        int level = 0;
        if(Se >= 0){
            level = countPerIaqi1(Se, 11);
        }
        return MonitorDto.builder().index(11).level(level).name("硒Se").num(Se).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getAs(double As) {
        int level = 0;
        if(As >= 0){
            level = countPerIaqi1(As, 12);
        }
        return MonitorDto.builder().index(12).level(level).name("砷As").num(As).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getWHg(double WHg) {
        int level = 0;
        if(WHg >= 0){
            level = countPerIaqi1(WHg, 13);
        }
        return MonitorDto.builder().index(13).level(level).name("汞WHg").num(WHg).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getCd(double Cd) {
        int level = 0;
        if(Cd >= 0){
            level = countPerIaqi1(Cd, 14);
        }
        return MonitorDto.builder().index(14).level(level).name("镉Cd").num(Cd).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getCr6(double Cr6) {
        int level = 0;
        if(Cr6 >= 0){
            level = countPerIaqi1(Cr6, 15);
        }
        return MonitorDto.builder().index(15).level(level).name("六价铬Cr6").num(Cr6).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getWPb(double WPb) {
        int level = 0;
        if(WPb >= 0){
            level = countPerIaqi1(WPb, 16);
        }
        return MonitorDto.builder().index(16).level(level).name("铅WPb").num(WPb).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getCnTotal(double CnTotal) {
        int level = 0;
        if(CnTotal >= 0){
            level = countPerIaqi1(CnTotal, 17);
        }
        return MonitorDto.builder().index(17).level(level).name("氰化物CnTotal").num(CnTotal).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getVPhen(double VPhen) {
        int level = 0;
        if(VPhen >= 0){
            level = countPerIaqi1(VPhen, 18);
        }
        return MonitorDto.builder().index(18).level(level).name("挥发酚VPhen").num(VPhen).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getOils(double Oils) {
        int level = 0;
        if(Oils >= 0){
            level = countPerIaqi1(Oils, 19);
        }
        return MonitorDto.builder().index(19).level(level).name("石油类Oils").num(Oils).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getAnSAA(double AnSAA) {
        int level = 0;
        if(AnSAA >= 0){
            level = countPerIaqi1(AnSAA, 20);
        }
        return MonitorDto.builder().index(20).level(level).name("阴离子表面活性剂AnSAA").num(AnSAA).flag(true).priority(1)
                .build();
    }
    public static MonitorDto getS(double S) {
        int level = 0;
        if(S >= 0){
            level = countPerIaqi1(S, 21);
        }
        return MonitorDto.builder().index(21).level(level).name("硫化物S").num(S).flag(true).priority(0)
                .build();
    }
    public static MonitorDto getColoOrg(double ColoOrg) {
        int level = 0;
        if(ColoOrg >= 0){
            level = countPerIaqi1(ColoOrg, 22);
        }
        return MonitorDto.builder().index(22).level(level).name("粪大肠菌群(个/L)ColoOrg)").num(ColoOrg).flag(true).priority(1)
                .build();
    }

    /**
     * @Description: 河流、流域（水洗）水质定性评价分级
     *
     * @param idx13 I-Ⅲ水质比例
     * @param idx6 劣Ⅴ 水质比例
     * @return: int
     */
    public static int waterSystemEvaluate(double idx13, double idx6){
        if ( 0.90 <= idx13 ){
            return 1;
        }else if(0.75 <= idx13 && idx13 < 0.90){
            return  2;
        } else if(idx13 < 0.75 && (  idx6 < 0.20) ){
            return  3;
        } else if(idx13 < 0.75 && ( 0.20 <= idx6 && idx6 < 0.40) ){
            return  4;
        } else if(idx13 < 0.60 && ( 0.40 <= idx6) ){
            return  5;
        }
         return 5;
    }


    /**
     * @Description: 断面水质定性评价分级
     *
     * @param num 浓度指标
     * @return: int
     */
    public static int sectionEvaluate(int num){
        if ( num == 1 || num ==2 ){
            return 1;
        }else if( num == 3){
            return  2;
        } else if( num == 4 ){
            return  3;
        } else if( num == 5 ){
            return  4;
        }
        return 5;
    }

    public static void main(String[] args) {
        double[] doubles1 = aqiArr[7];
        double cp1 = 2.0;
        int num1 = 0;

        System.out.println("str  "+Arrays.toString(doubles1));
        for (int i = 1; i < doubles1.length; i++) {
            if (cp1 <= doubles1[i]) {
                if(doubles1[doubles1.length-1] == cp1 && cp1 ==doubles1[i]){
                    num1 = i+1;
                    break;
                }
                if (doubles1[i] == doubles1[i+1]){
                    num1 = i+1;
                    break;
                }else {
                    num1 = i+1;
                    break;
                }
            }
        }
        System.out.println("num  "+num1);

    }
    /**
     * 断面前3主要污染物
     */
    public static ResultDto sectionPrimaryPollutant(List<MonitorDto> collect){
        MonitorDto resultDto = collect.get(0);
        if (resultDto.getLevel() < 3){
            return ResultDto.builder().level(resultDto.getLevel()).keyPollutant(resultDto.getName()).num(resultDto.getNum()).build();
        }
        int size = collect.size() > 2 ? 3 : collect.size();
        return ResultDto.builder().level(resultDto.getLevel()).keyPollutant(resultDto.getName()).num(resultDto.getNum()).list(collect.subList(0,size)).build();
    }
    /**
     * 流域前3主要污染物
     */
    public static ResultDto basinPrimaryPollutant(List<MonitorDto> collect){
        MonitorDto resultDto = collect.get(0);
        int size = collect.size() > 2 ? 3 : collect.size();
        if (size < 3){
            return ResultDto.builder().level(resultDto.getLevel()).keyPollutant(resultDto.getName()).num(resultDto.getNum()).build();
        }
        return ResultDto.builder().level(resultDto.getLevel()).keyPollutant(resultDto.getName()).num(resultDto.getNum()).list(collect.subList(0,size)).build();
    }




    /**
     * 计算超标倍数
     */
    public static double CmpSpstandardMultiple(MonitorDto collect){
        double sub = subDouble(collect.getNum(), aqiArr[collect.getIndex()][2]);
        return divide(sub, aqiArr[collect.getIndex()][2]);
    }
    /**
     * 计算断面超标率
     */
    public static double CmpSectionUnqualifiedRate(int a,int b){
        return new BigDecimal((float)a/b).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * Double相减
     */
    public static double subDouble(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * 两个double类型的数相除，保留两位小数
     */
    public static double divide(double a, double b){
        return new BigDecimal((float)a/b).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }



    /**
     * 计算平均值
     */
    public static DataDto secTionNumLessThan5(List<DataDto> avgList){
        Double CODCr = avgList.stream().collect(Collectors.averagingDouble((n -> n.getCODCr())));
        Double CODMn = avgList.stream().collect(Collectors.averagingDouble(n -> n.getCODMn()));
        Double DO = avgList.stream().collect(Collectors.averagingDouble((n -> n.getDO())));
        Double BOD5 = avgList.stream().collect(Collectors.averagingDouble((n -> n.getBOD5())));
        Double NH4N = avgList.stream().collect(Collectors.averagingDouble((n -> n.getNH4N())));
        Double dPTotal =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getPTotal())));
        Double dNTotal =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getNTotal())));
        Double dWCu =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getWCu())));
        Double dWZn =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getWZn())));
        Double dF =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getF())));
        Double dSe =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getSe())));
        Double dAs =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getAs())));
        Double dWHg = avgList.stream().collect(Collectors.averagingDouble((n -> n.getWHg())));
        Double dCd =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getCd())));
        Double dCr6 =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getCr6())));
        Double dWPb = avgList.stream().collect(Collectors.averagingDouble((n -> n.getWPb())));
        Double dCnTotal =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getCnTotal())));
        Double dVPhen =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getWPhen())));
        Double dOils = avgList.stream().collect(Collectors.averagingDouble((n -> n.getOils())));
        Double dAnSAA =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getAnSAA())));
        Double dS =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getS())));
        Double dColoOrg =  avgList.stream().collect(Collectors.averagingDouble((n -> n.getColoOrg())));
     return    new  DataDto( avgList.get(0).getType(),avgList.get(0).getSerialNo(), CODMn, DO, CODCr, BOD5, NH4N, dPTotal, dNTotal, dWCu, dWZn, dF, dSe, dAs, dWHg, dCd, dCr6, dWPb, dCnTotal, dVPhen, dOils, dAnSAA, dS, dColoOrg);
//        return new DataDto(avgList.get(0).getType(),avgList.get(0).getSerialNo(), BOD5, NH4N, CODMn, DO, CODCr);
    }





}


