package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 管段缺陷评估
 */
@Data
public class EngPipeEvalDto extends IdDto {




     @ExcelProperty(value = "起始井")
    @ApiModelProperty(value="起始井")
    private String beginWell;


     @ExcelProperty(value = "终止井")
    @ApiModelProperty(value="终止井")
    private String endWell;


     @ExcelProperty(value = "缺陷类型")
    @ApiModelProperty(value="缺陷类型")
    private Long defectType;

     
     @ExcelProperty(value = "缺陷位置纵向")
    @ApiModelProperty(value="缺陷位置纵向")
    private String defectX;

    
     @ExcelProperty(value = "缺陷位置横向")
    @ApiModelProperty(value="缺陷位置横向")
    private String defectY;

   
     @ExcelProperty(value = "缺陷等级")
    @ApiModelProperty(value="缺陷等级")
    private Long defectLevel;

    
     @ExcelProperty(value = "修护建议")
    @ApiModelProperty(value="修护建议")
    private String remark;

    
     @ExcelProperty(value = "修护方式")
    @ApiModelProperty(value="修护方式")
    private String defectMeth;

    

     @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
     @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

   
     @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
     @ExcelProperty(value = "修改时间",converter = CustomConverter.class)
     @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private   LocalDateTime updateTime;

     @ExcelProperty(value = "创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

     @ExcelProperty(value = "修改人")
    @ApiModelProperty(value="修改人")
    private String updateUser;



     @ExcelProperty(value = "工程Id")
    @ApiModelProperty(value="工程Id")
     @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;

    
}