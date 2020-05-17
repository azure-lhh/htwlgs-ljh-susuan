package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PumpMonitorPageDto extends IdDto{

    @ApiModelProperty(value = "站点名称",required = true)
    private String stationName;

    @ApiModelProperty(value = "站点编号",required = true)
    private String stationCode;

    @ApiModelProperty(value = "所属行政区划编码",required = true)
    private String areaCode;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("纬度")
    private Integer signalNum;

    @ApiModelProperty("实时（最新）监测数据")
    private PumpMonitorDataDto monitorData;

    @ApiModelProperty("蓄水量 统计列表")
    private List<StatisticsVarCherDto> storageList;

    @ApiModelProperty("流量 统计列表")
    private List<StatisticsVarCherDto> trafficList;
}
