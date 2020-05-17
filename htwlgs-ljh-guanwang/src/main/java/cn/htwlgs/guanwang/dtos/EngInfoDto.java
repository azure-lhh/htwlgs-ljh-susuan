package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName EngInfoDto
 * @Description 缺陷检查工程Dto
 * @Author lihouhai
 * @Date 2020/4/29 15:02
 * @Version 1.0
 */
@Data
public class EngInfoDto extends IdDto {



    @ExcelProperty(value = "工程名称")
    @ApiModelProperty(value=  "工程名称")
    private String proName;

    @ExcelProperty(value = "行政区域编号")
    @ApiModelProperty(value="行政区域编号")
    private String areaCode;

    @ExcelProperty(value = "管段编号")
    @ApiModelProperty(value="管段编号")
    @NotBlank(message = "编号不能为空")
    private String proPart;

    @ExcelProperty(value = "工程地址")
    @ApiModelProperty(value="工程地址")
    private String address;

    @ExcelProperty(value = "委托单位")
    @ApiModelProperty(value="委托单位")
    private String entrustUnit;

    @ExcelProperty(value = "检测单位")
    @ApiModelProperty(value="检测单位")
    private String detectUnit;

    @ApiModelProperty(value = "检测时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "检测时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detectTime;

    @ExcelProperty(value = "创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

    @ExcelProperty(value = "修改人")
    @ApiModelProperty(value="修改人")
    private String updateUser;


    @ExcelProperty(value = "工程信息")
    @ApiModelProperty(value="工程信息类型0、管段1、检查井2、排污口")
    @NotNull(message = "编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer type;


    @ApiModelProperty(value="检查工程0 缺陷工程1")
    @NotNull(message = "工程类型不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engType;

}
