package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class InterceptingStatisticsDto {

    @ApiModelProperty("内底标高统计列表")
    private List<StatisticsVarCherDto> insoleList;

    @ApiModelProperty("进流量统计列表")
    private List<StatisticsVarCherDto> flowList;

    @ApiModelProperty("积水面积统计列表")
    private List<StatisticsVarCherDto> areaList;
}
