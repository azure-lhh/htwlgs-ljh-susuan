package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class StorageTankDto extends IdDto{



    @ApiModelProperty(value = "调蓄池名称")
    @ExcelProperty(value = "调蓄池名称")
    @NotBlank(message = "调蓄池名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String tankName;


    @ApiModelProperty(value = "所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    @NotBlank(message = "行政区划不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    @ApiModelProperty(value = "地址")
    @ExcelProperty(value = "地址")
    private String address;


    @ApiModelProperty(value = "调蓄池编号")
    @ExcelProperty(value = "调蓄池编号")
    @NotBlank(message = "调蓄池不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String tankCode;


    @ApiModelProperty(value = "内底标高")
    @ExcelProperty(value = "内底标高")
    private BigDecimal insoleElevation;


    @ApiModelProperty(value = "进流量")
    @ExcelProperty(value = "进流量")
    private BigDecimal inflow;


    @ApiModelProperty(value = "积水面积")
    @ExcelProperty(value = "积水面积")
    private BigDecimal catchmentArea;


    @ApiModelProperty(value = "初始深度")
    @ExcelProperty(value = "初始深度")
    private BigDecimal initialDepth;


    @ApiModelProperty(value = "最大深度")
    @ExcelProperty(value = "最大深度")
    private BigDecimal maxDepth;


    @ApiModelProperty(value = "超载深度")
    @ExcelProperty(value = "超载深度")
    private BigDecimal overloadDepth;


    @ApiModelProperty(value = "经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;


    @ApiModelProperty(value = "纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    private LocalDateTime setupTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",converter =CustomConverter.class)
    private LocalDateTime createTime;

    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty(value = "修改时间",converter =CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


}
