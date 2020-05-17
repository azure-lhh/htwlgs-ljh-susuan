package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName PFTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/24 14:29
 * @Version 1.0
 */
public enum PFTypeEnum  {
    PF_TYPE_1("排放类型1"),
    PF_TYPE_2("排放类型2");
    private String val;

    PFTypeEnum() {
    }

    PFTypeEnum( String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }




    public static PFTypeEnum getEnumData(String string){
        for(PFTypeEnum typeEnum:PFTypeEnum.values()) {
            if (typeEnum.getVal().equals(string)){
                return typeEnum;
            }
        }
        throw  new BusinessException("污水口排放类型数据错误");
    }


}
