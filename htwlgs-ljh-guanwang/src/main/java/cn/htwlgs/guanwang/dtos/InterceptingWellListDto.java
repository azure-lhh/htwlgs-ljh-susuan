package cn.htwlgs.guanwang.dtos;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class InterceptingWellListDto extends IdDto{


    @ApiModelProperty(value = "截流井名称")
    @ExcelProperty(value = "截流井名称")
    private String wellName;


    @ApiModelProperty(value = "所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    private String areaCode;

    @ApiModelProperty(value = "地址")
    @ExcelProperty(value = "地址")
    private String address;


    @ApiModelProperty(value = "编号")
    @ExcelProperty(value = "编号")
    private String wellCode;


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
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
