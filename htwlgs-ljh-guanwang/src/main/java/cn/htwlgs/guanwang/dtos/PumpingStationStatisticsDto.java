package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PumpingStationStatisticsDto {

    @ApiModelProperty("水位统计列表")
    private List<StatisticsVarCherDto> wlList;

    @ApiModelProperty("流量统计列表")
    private List<StatisticsVarCherDto> trafficList;

    @ApiModelProperty("开关统计列表")
    private List<StatisticsVarCherDto> signalList;
}
