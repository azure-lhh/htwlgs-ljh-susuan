package cn.htwlgs.bigdata.dtos.enums;

/**
 * @ClassName RiverTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/5/13 16:41
 * @Version 1.0
 */
public enum  RiverTypeEnum {
      RIVER_1,//湖库
    RIVER_2;
      public static RiverTypeEnum  getTypeOrdinal(int ordinal){
          for (RiverTypeEnum typeEnum:RiverTypeEnum.values()) {
              if (typeEnum.ordinal() == ordinal){
                  return typeEnum;
              }
          }
          return RiverTypeEnum.RIVER_2;
      }

}
