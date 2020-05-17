package cn.htwlgs.guanwang.dtos;


import java.math.BigDecimal;
import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import io.swagger.annotations.*;
import lombok.Data;
import com.alibaba.excel.annotation.ExcelProperty;
import cn.htwlgs.guanwang.entity.PumpMonitorWater;

import javax.validation.constraints.NotEmpty;

/**
 * 管网泵站监测--水泵表(PumpMonitorWater)实体类
 *
 * @author makejava
 * @since 2020-05-13 10:55:16
 */
@Data
public class PumpMonitorWaterDto extends IdDto{

    @ApiModelProperty(value = "泵站id",required = true)
    @ExcelProperty(value = "泵站id")
    @NotEmpty(message = "泵站id不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private Integer stationId;

    @ApiModelProperty(value = "水泵名称",required = true)
    @ExcelProperty(value = "水泵名称")
    @NotEmpty(message = "水泵名称不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String pumpName;

    @ApiModelProperty(value = "水泵编号",required = true)
    @ExcelProperty(value = "水泵编号")
    @NotEmpty(message = "水泵编号不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String pumpCode;

    @ApiModelProperty("水泵类型")
    @ExcelProperty(value = "水泵类型")
    private Integer pumpType;

    @ApiModelProperty("流量")
    @ExcelProperty(value = "流量")
    private BigDecimal traffic;

    @ApiModelProperty("扬程")
    @ExcelProperty(value = "扬程")
    private BigDecimal waterHead;

    @ApiModelProperty("功率")
    @ExcelProperty(value = "功率")
    private BigDecimal power;

    @ApiModelProperty("近期设置流量--旱季")
    @ExcelProperty(value = "近期设置流量--旱季")
    private BigDecimal traDrought;

    @ApiModelProperty("近期设置流量--雨季")
    @ExcelProperty(value = "近期设置流量--雨季")
    private BigDecimal traRain;

    @ApiModelProperty("开关信号")
    @ExcelProperty(value = "开关信号")
    private String signal;

    @ApiModelProperty("电压")
    @ExcelProperty(value = "电压")
    private BigDecimal voltage;

    @ApiModelProperty("电流")
    @ExcelProperty(value = "电流")
    private BigDecimal current;

    @ApiModelProperty("开关量")
    @ExcelProperty(value = "开关量")
    private BigDecimal switchAmount;

    public PumpMonitorWaterDto() {}

    public PumpMonitorWaterDto(PumpMonitorWater item) {
        CopyUtils.copyProperties(item,this);
        if(item.getSignal() == 0){
            this.setSignal(Constants.PUMPING_STATION_SIGNAL);
        }else{
            this.setSignal(Constants.PUMPING_STATION_SIGNAL_ON);
        }
    }
}