package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

public enum EngineeringTypeEnum {
    ENG_JZ("检查工程"),
    ENG_QX("缺陷工程");
    private String val;

    EngineeringTypeEnum(String val) {
        this.val = val;
    }

    EngineeringTypeEnum() {
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    public static EngineeringTypeEnum getEnumOrdinal(int ordinal){
        for(EngineeringTypeEnum typeEnum:EngineeringTypeEnum.values()) {
            if (ordinal == typeEnum.ordinal()){
                return typeEnum;
            }
        }
        throw  new BusinessException("未找到工程类别信息(缺陷/检查)");
    }
}
