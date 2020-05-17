package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PumpingStatisticsDto {
    @ApiModelProperty("泵站开启 数值")
    private BigDecimal signalOff;

    @ApiModelProperty("泵站关闭 数值")
    private BigDecimal signalOn;

    @ApiModelProperty("水位最大 数值")
    private BigDecimal wlMax;

    @ApiModelProperty("水位最小 数值")
    private BigDecimal wlMin;

    @ApiModelProperty("流量最大 数值")
    private BigDecimal trafficMax;

    @ApiModelProperty("流量最小 数值")
    private BigDecimal trafficMin;
}
