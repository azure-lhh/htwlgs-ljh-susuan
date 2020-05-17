package cn.htwlgs.guanwang.dtos.enums;

import cn.htwlgs.common.exception.BusinessException;

/**
 * @ClassName ShapeTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/23 12:56
 * @Version 1.0
 */
public enum  ShapeTypeEnum {
    CIRCULAR("圆形"),
    SQUARE("正方形");
    private String val;
    ShapeTypeEnum() {
    }

    ShapeTypeEnum( String val) {
        this.val = val;
    }


    public String getVal() {
        return val;
    }

    public static ShapeTypeEnum getEnumData(String string){
        for(ShapeTypeEnum typeEnum:ShapeTypeEnum.values()) {
            if (typeEnum.getVal().equals(string)){
                return typeEnum;
            }
        }
      throw  new BusinessException("形状类型数据错误");
    }



}
