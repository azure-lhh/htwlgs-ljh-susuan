package cn.htwlgs.guanwang.dtos.enums;


import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName AttachmentTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/29 14:44
 * @Version 1.0
 */
public enum  AttachmentTypeEnum {
    ENG("子工程"),
    ENG_HZ("总工程");
    private String val;

    AttachmentTypeEnum(String val) {
        this.val = val;
    }

    AttachmentTypeEnum() {
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }


    public static AttachmentTypeEnum getEnumOrdinal(int ordinal){
        for(AttachmentTypeEnum typeEnum:AttachmentTypeEnum.values()) {
            if (ordinal == typeEnum.ordinal()){
                return typeEnum;
            }
        }
        throw  new BusinessException("未找到附件工程类别信息");
    }
}
