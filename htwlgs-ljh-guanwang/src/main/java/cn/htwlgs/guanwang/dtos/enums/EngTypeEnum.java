package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName EngTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/30 11:24
 * @Version 1.0
 */
public enum  EngTypeEnum {
    SEWAG_OUTLET("排污口"),
    MANHOLE("检查井"),
    PART("管网管段");
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    EngTypeEnum() {
    }

    EngTypeEnum(String val) {
        this.val = val;
    }

    public static EngTypeEnum getEnumOrdinal(int ordinal){
        for(EngTypeEnum typeEnum:EngTypeEnum.values()) {
            if (ordinal == typeEnum.ordinal()){
                return typeEnum;
            }
        }
        throw  new BusinessException("未找到子工程类别信息");
    }
}
