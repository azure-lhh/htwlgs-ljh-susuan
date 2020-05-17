package cn.htwlgs.bigdata.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


/**
 * @ClassName MonitorDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/12 13:49
 * @Version 1.0
 */

@Setter
@Getter
@Builder(toBuilder=true)
public class MonitorDto {
    /**
     * 污染物指标所在下标
     */
    @JsonIgnore
    private int index;

    @ApiModelProperty(value = "污染物名称")
    private  String name;

    @ApiModelProperty(value = "污染物指标浓度等级")
    private  Integer level;

    @ApiModelProperty(value = "污染物指标浓度")
    private Double  num;

    @ApiModelProperty(value = "超标倍数/断面超标率")
    private Double sprstd;

    /**
     *水温、pH值和溶解氧等项目不计算超标倍数 设置false
     */
    @JsonIgnore
    private boolean flag = true;
    /**
     *当氰化物或铅、铬等重金属超标时，优先作为主要污染指 设置1
     */
    @JsonIgnore
    private Integer priority;//优先级重金属

    public MonitorDto() {
    }

    public MonitorDto(Double sprstd) {
        this.sprstd = sprstd;
    }

    public MonitorDto(int index, String name, Integer level, Double num,boolean flag) {
        this.index = index;
        this.name = name;
        this.level = level;
        this.num = num;
        this.flag = flag;
    }


    public MonitorDto(int index, String name, Integer level, Double num, Double sprstd, boolean flag, Integer priority) {
        this.index = index;
        this.name = name;
        this.level = level;
        this.num = num;
        this.sprstd = sprstd;
        this.flag = flag;
        this.priority = priority;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public MonitorDto(String name, Integer level, Double num) {
        this.name = name;
        this.level = level;
        this.num = num;
    }
}
