package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RainwaterStatisticsDto {
    @ApiModelProperty("管网总长")
    private BigDecimal lengthSum;

    @ApiModelProperty("今年新增管网总长")
    private BigDecimal lengthAdd;

    @ApiModelProperty("管道埋深 统计列表")
    private List<StatisticsVarCherDto> depthList;
}
