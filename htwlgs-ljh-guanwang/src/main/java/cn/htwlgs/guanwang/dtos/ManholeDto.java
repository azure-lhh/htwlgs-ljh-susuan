package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.enums.ShapeTypeEnum;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName ManholeDto
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 9:54
 * @Version 1.0
 */
@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class ManholeDto {
   
    @ApiModelProperty("序号")
    @ExcelProperty(value = "序号")
    @NotNull(message = "序号不能为空",groups = UpdateAttr.class)
    @Min(value = 0)
    private Integer id;


    @ApiModelProperty(value = "名称",example = "检查井001")
    @ExcelProperty(value = "名称")
    @NotBlank(message = "名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String name;

    
    @ApiModelProperty(value = "行政区编码",example = "ZJXV001")
    @ExcelProperty(value = "行政区编码")
    @NotBlank(message = "工程类型行政区编码不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

 
    @ApiModelProperty(value = "检查井地址",example = "光电园")
    @ExcelProperty(value = "检查井地址")
    private String address;

    
    @ApiModelProperty(value = "检查井编号",example = "XC002")
    @ExcelProperty(value = "检查井编号")
    @NotBlank(message = "检查井编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String serialNo;

    
    @ApiModelProperty(value = "井深",example = "123.3")
    @ExcelProperty(value = "井深")
    private Double depth;

    
    @ApiModelProperty(value = "管理单位code",example = "重庆市环保局")
    @ExcelProperty(value = "管理单位code")
    private String manUnit;

    
    @ApiModelProperty(value = "井材质",example = "铁")
    @ExcelProperty(value = "井材质")
    private String material;

    
    @ApiModelProperty(value = "创建人",example = "六")
    @ExcelProperty(value = "创建人")
    private String createUser;

    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "埋深时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "埋深时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "修改时间",converter =  CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "修改人",example = "王")
    @ExcelProperty(value = "修改人")
    private String updateUser;

    
    @ApiModelProperty(value = "经度",example = "123.363")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    
    @ApiModelProperty(value = "维度",example = "123.3645")
    @ExcelProperty(value = "维度")
    private BigDecimal lat;

    
    @ApiModelProperty(value = "井盖形状",example = "圆形")
    @ExcelProperty(value = "井盖形状")
    private String mheShape;


    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;




}
