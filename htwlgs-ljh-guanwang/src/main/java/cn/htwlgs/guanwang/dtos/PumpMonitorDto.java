package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.PumpMonitor;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

/**
 * 管网泵站监测表(PumpMonitor)实体类
 *
 * @author makejava
 * @since 2020-05-13 10:13:51
 */
@Data
public class PumpMonitorDto extends IdDto{

    @ApiModelProperty(value = "站点名称",required = true)
    @ExcelProperty(value = "站点名称")
    @NotEmpty(message = "站点名称不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String stationName;
   
    @ApiModelProperty(value = "站点编号",required = true)
    @ExcelProperty(value = "站点编号")
    @NotEmpty(message = "站点编号不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String stationCode;
   
    @ApiModelProperty(value = "所属行政区划编码",required = true)
    @ExcelProperty(value = "所属行政区划编码")
    @NotEmpty(message = "所属行政区划编码不能为空",groups = {InsertAttr.class, UpdateAttr.class})
    private String areaCode;

    @ApiModelProperty("地址")
    @ExcelProperty(value = "地址")
    private String address;

    @ApiModelProperty("经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;
   
    @ApiModelProperty("纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ApiModelProperty("占地面积")
    @ExcelProperty(value = "占地面积")
    private BigDecimal areaCovered;

    @ApiModelProperty("流量")
    @ExcelProperty(value = "流量")
    private BigDecimal traffic;

    @ApiModelProperty("排水体制")
    @ExcelProperty(value = "排水体制")
    private String drain;

    @ApiModelProperty("运营单位名称")
    @ExcelProperty(value = "运营单位名称")
    private String departmentName;

    @ApiModelProperty("运营单位联系人")
    @ExcelProperty(value = "运营单位联系人")
    private String departmentUser;

    @ApiModelProperty("运营单位联系方式")
    @ExcelProperty(value = "运营单位联系方式")
    private String departmentPhone;

    @ApiModelProperty("通信传输设备")
    @ExcelProperty(value = "通信传输设备")
    private String communication;

    @ApiModelProperty("监测因子code")
    @ExcelProperty(value = "监测因子code")
    private String factorCode;

    @ApiModelProperty("监测频次")
    @ExcelProperty(value = "监测频次")
    private String frequency;

    @ApiModelProperty("设备品牌")
    @ExcelProperty(value = "设备品牌")
    private String brand;

    @ApiModelProperty("设备型号")
    @ExcelProperty(value = "设备型号")
    private String modelNum;

    @ApiModelProperty("站房面积")
    @ExcelProperty(value = "站房面积")
    private BigDecimal buildingArea;

    @ApiModelProperty("站房至采样点距离")
    @ExcelProperty(value = "站房至采样点距离")
    private BigDecimal distance;

    @ApiModelProperty("采样管线长度")
    @ExcelProperty(value = "采样管线长度")
    private BigDecimal length;

    @ApiModelProperty("数据采集仪 品牌")
    @ExcelProperty(value = "数据采集仪 品牌")
    private String collectionBrand;

    @ApiModelProperty("数据采集仪 传输协议")
    @ExcelProperty(value = "数据采集仪 传输协议")
    private String transport;

    @ApiModelProperty("数据采集仪 型号")
    @ExcelProperty(value = "数据采集仪 型号")
    private String collectionModel;

    @ApiModelProperty("输出型号类型")
    @ExcelProperty(value = "输出型号类型")
    private Integer outType;

    public PumpMonitorDto() {}

    public PumpMonitorDto(PumpMonitor item) {
        CopyUtils.copyProperties(item,this);
    }
}