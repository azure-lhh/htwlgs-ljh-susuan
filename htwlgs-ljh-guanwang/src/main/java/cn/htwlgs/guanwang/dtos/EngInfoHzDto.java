package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.utils.SurpassmFile;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class EngInfoHzDto extends IdDto{




    @ExcelProperty(value = "工程名称")
    @ApiModelProperty(value=  "工程名称")
    @NotBlank(message = "工程名称号不能为空")
    private String proName;

    @ExcelProperty(value = "行政区域编号")
    @ApiModelProperty(value="行政区域编号")
    @NotBlank(message = "行政区域编号不能为空")
    private String areaCode;

    @ExcelProperty(value = "管段编号")
    @ApiModelProperty(value="管段编号")
    @NotBlank(message = "管段编号不能为空")
    private String proPart;

    @ExcelProperty(value = "工程地址")
    @ApiModelProperty(value="工程地址")
    @NotBlank(message ="工程地址不能为空")
    private String address;

    @ExcelProperty(value = "委托单位")
    @ApiModelProperty(value="委托单位")
    @NotBlank(message = "委托单位不能为空")
    private String entrustUnit;

    @ExcelProperty(value = "检测单位")
    @ApiModelProperty(value="检测单位")
    @NotBlank(message = "检测单位不能为空")
    private String detectUnit;

    @ExcelProperty(value = "检测时间" ,converter = CustomConverter.class)
    @ApiModelProperty(value="检测时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detectTime;

//    @ExcelProperty(value = "附件地址")
//    @ApiModelProperty(value="附件地址")
//    private String attachment;
//    @ExcelProperty(value = "附件地址")
//    @ApiModelProperty(value="附件地址")
//    private List<SurpassmFile>  fileList;


    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "修改时间",converter =  CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ExcelProperty(value = "创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

    @ExcelProperty(value = "修改人")
    @ApiModelProperty(value="修改人")
    private String updateUser;


    @ExcelProperty(value = "工程信息")
    @ApiModelProperty(value="工程信息类型0、管段1、检查井2、排污口")
    private Integer type;



    @ApiModelProperty(value="检查工程0 缺陷工程1")
    @NotNull(message = "不能为空")
    @Min(value = 0)
    private Integer engType;






}
