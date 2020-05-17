package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LevelMonitorPageDto extends LevelMonitorDataDto{

    @ApiModelProperty(value = "站点名称")
    private String stationName;

    @ApiModelProperty(value = "站点编号")
    private String stationCode;

    @ApiModelProperty(value = "所属行政区划编码")
    private String areaCode;

    @ApiModelProperty("水位统计列表")
    private List<StatisticsVarCherDto> wlList;

    @ApiModelProperty("流量统计列表")
    private List<StatisticsVarCherDto> trafficList;
}
