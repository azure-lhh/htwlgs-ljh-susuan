package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;



@Data
public class RainwaterStatisticsEntityDto {

    @ApiModelProperty("管网总长")
    private BigDecimal lengthSum;

    @ApiModelProperty("今年新增管网总长")
    private BigDecimal lengthAdd;

    @ApiModelProperty("管道埋深 最大值")
    private BigDecimal depthMax;

    @ApiModelProperty("管道埋深 最小值")
    private BigDecimal depthMin;

    public RainwaterStatisticsEntityDto() {
    }

    public RainwaterStatisticsEntityDto(BigDecimal lengthSum, BigDecimal lengthAdd, BigDecimal depthMax, BigDecimal depthMin) {
        this.lengthSum = lengthSum;
        this.lengthAdd = lengthAdd;
        this.depthMax = depthMax;
        this.depthMin = depthMin;
    }
}
