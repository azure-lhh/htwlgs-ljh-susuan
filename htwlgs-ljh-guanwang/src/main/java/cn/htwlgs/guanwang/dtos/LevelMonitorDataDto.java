package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.LevelMonitorData;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LevelMonitorDataDto extends IdDto{

    @ApiModelProperty("是否手动填报")
    @ExcelProperty(value = "是否手动填报")
    private Boolean artificial;

    @ApiModelProperty(value = "监测日期 yyyy-MM-dd",required = true)
    @ExcelProperty(value = "监测日期",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate monitorDate;

    @ApiModelProperty(value = "监测时间 HH:mm:ss",required = true)
    @ExcelProperty(value = "监测时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime monitorTime;

    @ApiModelProperty("水位")
    @ExcelProperty(value = "水位")
    private BigDecimal wl;

    @ApiModelProperty("流量")
    @ExcelProperty(value = "流量")
    private BigDecimal traffic;

    @ApiModelProperty("电压")
    @ExcelProperty(value = "电压")
    private BigDecimal voltage;

    @ApiModelProperty("电流")
    @ExcelProperty(value = "电流")
    private BigDecimal current;

    @ApiModelProperty("能耗")
    @ExcelProperty(value = "能耗")
    private String energy;

    @ApiModelProperty("开关信号")
    @ExcelProperty(value = "开关信号")
    private String signal;

    @ApiModelProperty(value = "站点id",required = true)
    @ExcelProperty(value = "站点id")
    @NotNull(message = "站点id不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private Integer stationId;

    public LevelMonitorDataDto() {
    }

    public LevelMonitorDataDto(LevelMonitorData item) {
        CopyUtils.copyProperties(item,this);
        if(item.getSignal() == 0){
            this.setSignal(Constants.PUMPING_STATION_SIGNAL);
        }else{
            this.setSignal(Constants.PUMPING_STATION_SIGNAL_ON);
        }
    }
}
