package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SepticTankStatisticsEntityDto {
    @ApiModelProperty("标高 最大数值")
    private BigDecimal markMax;

    @ApiModelProperty("标高 最小数值")
    private BigDecimal markMin;

    @ApiModelProperty("处理量 最大数值")
    private BigDecimal handleMax;

    @ApiModelProperty("处理量 最小数值")
    private BigDecimal handleMin;

    @ApiModelProperty("运行中 化粪池数值")
    private BigDecimal stateOff;

    @ApiModelProperty("未运行 化粪池数值")
    private BigDecimal stateOn;
}
