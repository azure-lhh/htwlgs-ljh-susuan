package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SepticTankStatisticsDto {

    @ApiModelProperty("标高 统计列表")
    private List<StatisticsVarCherDto> markList;

    @ApiModelProperty("处理量 统计列表")
    private List<StatisticsVarCherDto> handleList;

    @ApiModelProperty("运行状态 统计列表")
    private List<StatisticsVarCherDto> stateList;
}
