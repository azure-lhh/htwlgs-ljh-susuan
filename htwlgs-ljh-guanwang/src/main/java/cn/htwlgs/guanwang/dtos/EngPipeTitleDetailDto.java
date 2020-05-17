package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName 检测单
 * @Description
 * @Author lihouhai
 * @Date 2020/4/30 16:37
 * @Version 1.0
 */
@Data
public class EngPipeTitleDetailDto extends IdDto {



    /**
     * 距离
     */
    @ExcelProperty(value = "距离")
    @ApiModelProperty(value="距离")
    private BigDecimal dist;


    /**
     * 缺陷名称代码
     */
    @ExcelProperty(value = "缺陷名称代码")
    @ApiModelProperty(value="缺陷名称代码")
    private String nmcd;

    /**
     * 分值
     */
    @ExcelProperty(value = "分值")
    @ApiModelProperty(value="分值")
    private BigDecimal score;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    @ApiModelProperty(value="等级")
    private Long plevel;

    /**
     * 管道内部描述
     */
    @ExcelProperty(value = "管道内部描述")
    @ApiModelProperty(value="管道内部描述")
    private String pdesc;

    /**
     * 照片序号或者说明
     */
    @ExcelProperty(value = "照片序号或者说明")
    @ApiModelProperty(value="照片序号或者说明")
    private String picSerial;

    /**
     * 工程id
     */
    @ExcelProperty(value = "工程id")
    @ApiModelProperty(value="工程id")
    @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;


}
