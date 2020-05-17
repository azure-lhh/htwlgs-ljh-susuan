package cn.htwlgs.guanwang.dtos;


import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.PumpMonitorData;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
/**
 * 管网泵站监测--监测数据表(PumpMonitorData)实体类
 *
 * @author makejava
 * @since 2020-05-13 10:31:22
 */
@Data
public class PumpMonitorDataDto extends IdDto{

    @ApiModelProperty(value = "泵站id",required = true)
    @ExcelProperty(value = "泵站id")
    @NotNull(message = "泵站id不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private Integer stationId;

    @ApiModelProperty("是否手动填报")
    @ExcelProperty(value = "是否手动填报")
    private Boolean artificial;

    @ApiModelProperty(value = "监测日期",required = true)
    @ExcelProperty(value = "监测日期",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate monitorDate;

    @ApiModelProperty(value = "监测时间",required = true)
    @ExcelProperty(value = "监测时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime monitorTime;

    @ApiModelProperty("实时液位")
    @ExcelProperty(value = "实时液位")
    private BigDecimal level;

    @ApiModelProperty("流量")
    @ExcelProperty(value = "流量")
    private BigDecimal traffic;

    @ApiModelProperty("开关信号")
    @ExcelProperty(value = "开关信号")
    private String signal;

    @ApiModelProperty("蓄水量")
    @ExcelProperty(value = "蓄水量")
    private BigDecimal storage;

    @ApiModelProperty("开度大小")
    @ExcelProperty(value = "开度大小")
    private String opening;

    public PumpMonitorDataDto() {}

    public PumpMonitorDataDto(PumpMonitorData item) {
        CopyUtils.copyProperties(item,this);
        if(item.getSignal() == 0){
            this.setSignal(Constants.PUMPING_STATION_SIGNAL);
        }else{
            this.setSignal(Constants.PUMPING_STATION_SIGNAL_ON);
        }
    }
}