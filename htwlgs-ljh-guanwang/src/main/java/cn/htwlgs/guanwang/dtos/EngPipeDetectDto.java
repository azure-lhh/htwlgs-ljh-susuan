package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 管段缺陷详情表
 */
@Data
public class EngPipeDetectDto extends IdDto{



    
    @ExcelProperty(value = "管段编号")
     @ApiModelProperty(value= "管段编号")
    @NotBlank(message = "管段编号不能为空")
    private String partCode;

    
    @ExcelProperty(value = "管径")
     @ApiModelProperty(value= "管径")
    private BigDecimal partRadius;

    
    @ExcelProperty(value = "长度")
     @ApiModelProperty(value= "长度")
    private BigDecimal partLen;

    
    @ExcelProperty(value = "材质")
     @ApiModelProperty(value= "材质")
    private String partMaterial;

    
    @ExcelProperty(value = "埋深起点")
     @ApiModelProperty(value= "埋深起点")
    private String beginDepth;

    
    @ExcelProperty(value = "埋深终点")
     @ApiModelProperty(value= "埋深终点")
    private String endDepth;

    
    @ExcelProperty(value = "结构性缺陷平均值S")
     @ApiModelProperty(value= "结构性缺陷平均值S")
    private BigDecimal sAvg;

    
    @ExcelProperty(value = "最大值Smax")
     @ApiModelProperty(value= "最大值Smax")
    private BigDecimal sMax;

    
    @ExcelProperty(value = "缺陷等级")
     @ApiModelProperty(value= "缺陷等级")
    private Long sLevel;

    
    @ExcelProperty(value = "缺陷密度")
     @ApiModelProperty(value= "缺陷密度")
    private BigDecimal sDensity;

    
    @ExcelProperty(value = "修护指数RI")
     @ApiModelProperty(value= "修护指数RI")
    private BigDecimal culIndex;

    
    @ExcelProperty(value = "综合状况评价")
     @ApiModelProperty(value= "综合状况评价")
    private String sEval;

    
    @ExcelProperty(value = "功能性缺陷平均值Y")
     @ApiModelProperty(value= "功能性缺陷平均值Y")
    private BigDecimal gAvg;

    
    @ExcelProperty(value = "最大值Ymax")
     @ApiModelProperty(value= "最大值Ymax")
    private BigDecimal gMax;

    
    @ExcelProperty(value = "缺陷等级")
     @ApiModelProperty(value= "缺陷等级")
    private Long gLevel;

    
    @ExcelProperty(value = "缺陷密度")
     @ApiModelProperty(value= "缺陷密度")
    private BigDecimal gDensity;

    
    @ExcelProperty(value = "养护指数")
     @ApiModelProperty(value= "养护指数")
    private BigDecimal mataIndex;

    
    @ExcelProperty(value = "综合状况评价")
     @ApiModelProperty(value= "综合状况评价")
    private String gEval;

    

    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    

    @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "修改时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    
    @ExcelProperty(value = "创建人")
     @ApiModelProperty(value= "创建人")
    private String createUser;

    
    @ExcelProperty(value = "修改人")
     @ApiModelProperty(value= "修改人")
    private String updateUser;



    
    @ExcelProperty(value = "工程信息id")
     @ApiModelProperty(value= "工程信息id")
    @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;

    
}