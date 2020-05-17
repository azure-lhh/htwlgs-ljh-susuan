package cn.htwlgs.guanwang.dtos;


import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.PumpingStation;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class PumpingStationDto extends IdDto{

    @ApiModelProperty(value = "泵站名称",required = true)
    @ExcelProperty(value = "泵站名称")
    @NotEmpty(message = "泵站名称不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String stationName;

    @ApiModelProperty("所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    @NotBlank(message = "所属行政区不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    @ApiModelProperty("地址")
    @ExcelProperty(value = "地址")
    @NotBlank(message = "地址不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String address;

    @ApiModelProperty("水位-M")
    @ExcelProperty(value = "水位")
    private BigDecimal wl = BigDecimal.ZERO;

    @ApiModelProperty("流量")
    @ExcelProperty(value = "流量")
    private BigDecimal traffic = BigDecimal.ZERO;

    @ApiModelProperty("电压")
    @ExcelProperty(value = "电压")
    private BigDecimal voltage = BigDecimal.ZERO;

    @ApiModelProperty("电流")
    @ExcelProperty(value = "电流")
    private BigDecimal current = BigDecimal.ZERO;

    @ApiModelProperty("功率")
    @ExcelProperty(value = "功率")
    private BigDecimal power = BigDecimal.ZERO;

    @ApiModelProperty("开关信号")
    @ExcelProperty(value = "开关信号")
    private String signal;

    @ApiModelProperty("故障情况")
    @ExcelProperty(value = "故障情况")
    private String situation;

    @ApiModelProperty("经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ApiModelProperty("创建时间")
    @ColumnWidth(value = 18)
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ColumnWidth(value = 18)
    @ApiModelProperty("创建人")
    @ExcelProperty(value = "创建人")
    private String createUser;

    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty(value = "修改时间",converter =CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public PumpingStationDto() {
    }

    public PumpingStationDto(PumpingStation item) {
        BeanUtils.copyProperties(item,this);
        if(item.getSignal() == 0){
            this.setSignal(Constants.PUMPING_STATION_SIGNAL);
        }else{
            this.setSignal(Constants.PUMPING_STATION_SIGNAL_ON);
        }
    }
}
