package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName SewageTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 17:40
 * @Version 1.0
 */
public enum  SewageTypeEnum {
    INDUSTRIAL_SEWAGE("工业废水"),
    LIFE_SEWAGE("生活污水"),
    COMBINED_SEWAGE("混合废污水");
    private String val;

    SewageTypeEnum() {
    }

    SewageTypeEnum(String val) {
        this.val = val;
    }


    public String getVal() {
        return val;
    }


    public static SewageTypeEnum getEnumData(String string){
        for(SewageTypeEnum typeEnum:SewageTypeEnum.values()) {
            if (typeEnum.getVal().equals(string)){
                return typeEnum;
            }
        }
        throw  new BusinessException("污水口类型数据错误");
    }
}
