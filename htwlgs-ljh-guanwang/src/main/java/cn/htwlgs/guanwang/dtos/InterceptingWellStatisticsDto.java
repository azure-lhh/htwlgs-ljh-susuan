package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 调蓄池和截流井字段一样，所以共用的dto，若字段发生改变致不同 需新建调蓄池dto
 */
@Data
public class InterceptingWellStatisticsDto {
    @ApiModelProperty("内底标高最大")
    private BigDecimal insoleMax;

    @ApiModelProperty("内底标高最小")
    private BigDecimal insoleMin;

    @ApiModelProperty("进流量最大 数值")
    private BigDecimal flowMax;

    @ApiModelProperty("进流量最小 数值")
    private BigDecimal flowMin;

    @ApiModelProperty("积水面积最大 数值")
    private BigDecimal araeMax;

    @ApiModelProperty("积水面积最小 数值")
    private BigDecimal araeMin;
}
