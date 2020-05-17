package cn.htwlgs.guanwang.dtos.enums;

/**
 * @ClassName AdminRegionTypeEnum
 * @Description
 * @Author lihouhai
 * @Date 2020/4/24 14:07
 * @Version 1.0
 */
public enum AdminRegionTypeEnum {

    PROVINCE(1,"省级"),
    CITY(2,"县级"),
    DISTRICT(3,"乡级")
    ;
    private int  cc;//类型
    private String xzdj;

    AdminRegionTypeEnum() {
    }

    AdminRegionTypeEnum(int cc, String xzdj) {
        this.cc = cc;
        this.xzdj = xzdj;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getXzdj() {
        return xzdj;
    }

    public void setXzdj(String xzdj) {
        this.xzdj = xzdj;
    }
}
