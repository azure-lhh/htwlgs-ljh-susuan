package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 附件表
 */
@Data
public class AttachmentDto extends IdDto{


    
    @ApiModelProperty(value = "文件名称",example = "文件名称")
    @ExcelProperty(value = "文件名称")
    @NotBlank(message = "文件名称不能为空",groups ={UpdateAttr.class, InsertAttr.class})
    private String fname;


    @ApiModelProperty(value = "上传描述",example = "上传描述")
    @ExcelProperty(value = "上传描述")
    private String fdescribe;


    @ApiModelProperty(value = "上传文件格式类型",example = "上传文件格式类型")
    @ExcelProperty(value = "格式类型")
    @NotBlank(message = "文件格式类型不能错误",groups ={UpdateAttr.class, InsertAttr.class})
    private String type;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "上传时间list",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "上传时间list")
    private String uploadTime;


    @ApiModelProperty(value = "名称",example = "检查井001")
    @ExcelProperty(value = "存储文件地址")
    private String uploadUrl;



    @ApiModelProperty(value = "工程id",example = "工程id",required = true)
    @ExcelProperty(value = "工程id")
    @NotNull(message = "工程类型不能为空",groups ={UpdateAttr.class, InsertAttr.class})
    private Integer engId;

    
    @ApiModelProperty(value = "工程类型 0子工程 1总工程 ",required = true)
    @ExcelProperty(value = "工程类型")
    @NotNull(message = "工程类型不能为空",groups ={UpdateAttr.class,InsertAttr.class})
    private Integer engType;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "修改时间",converter =  CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;



    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "创建时间",converter =  CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "创建人",example = "创建人001")
    @ExcelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty(value = "修改人",example = "修改人001")
    @ExcelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "多文件名称")
    @ExcelProperty(value = "多文件名称")
    private String fnameList;

    @ApiModelProperty(value = "多文件大小")
    @ExcelProperty(value = "多文件大小")
    private String fsizeList;

    
}