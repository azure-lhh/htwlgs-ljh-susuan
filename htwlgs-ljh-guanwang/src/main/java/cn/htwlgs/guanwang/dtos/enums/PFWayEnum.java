package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

public enum PFWayEnum {

    SEPARTATE("分流制"),
    COMBINE("合流制");
    private String val;

    PFWayEnum() {
    }

    PFWayEnum( String val) {
        this.val = val;
    }



    public String getVal() {
        return val;
    }


    public static PFWayEnum getEnumData(String string){
        for(PFWayEnum typeEnum:PFWayEnum.values()) {
            if (typeEnum.getVal().equals(string)){
                return typeEnum;
            }
        }
        throw  new BusinessException("污水口排放方式数据错误");
    }


    public static String getShapeEnumVal(PFWayEnum typeEnum){
        for(PFWayEnum shapeTypeEnum:PFWayEnum.values()) {
            if (typeEnum==shapeTypeEnum){
                return shapeTypeEnum.getVal();
            }
        }
        return PFWayEnum.COMBINE.getVal();
    }




}
