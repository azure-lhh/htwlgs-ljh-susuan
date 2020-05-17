package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName GWLevelEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/23 10:57
 * @Version 1.0
 */
public enum  GWLevelEnum {
    GW_LEVEL_1("一级"),
    GW_LEVEL_2("二级"),
    GW_LEVEL_3("三级");
    private String val;
    GWLevelEnum() {
    }

    GWLevelEnum( String val) {
        this.val = val;
    }


    public String getVal() {
        return val;
    }


    public static GWLevelEnum getEnumData(String string){
        for(GWLevelEnum levelEnum:GWLevelEnum.values()) {
            if (levelEnum.getVal().equals(string)){
                return levelEnum;
            }
        }
        throw  new BusinessException("管道级别类型错误");
    }


}
