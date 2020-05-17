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
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName 检测单
 * @Description
 * @Author lihouhai
 * @Date 2020/4/30 16:37
 * @Version 1.0
 */
@Data
public class EngPipeTitleDto extends IdDto {




    /**
     * 检测方法
     */
    @ExcelProperty(value = "检测方法")
    @ApiModelProperty(value="检测方法")
    private Long detectMeth;

    /**
     * 起始井号
     */
    @ExcelProperty(value = "起始井号")
    @ApiModelProperty(value="起始井号")
    private String beginWell;

    /**
     * 终止井号
     */
    @ExcelProperty(value = "终止井号")
    @ApiModelProperty(value="终止井号")
    private String endWell;

    /**
     * 建设年代
     */
    @ApiModelProperty(value = "建设年代",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "建设年代",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime steupTime;

    /**
     * 起点埋深
     */
    @ExcelProperty(value = "起点埋深")
    @ApiModelProperty(value="起点埋深")
    private BigDecimal beginDepth;

    /**
     * 终止埋深
     */
    @ExcelProperty(value = "终止埋深")
    @ApiModelProperty(value="终止埋深")
    private BigDecimal endDepth;

    /**
     * 管段类型
     */
    @ExcelProperty(value = "管段类型")
    @ApiModelProperty(value="管段类型")
    private String partType;

    /**
     * 管段材质
     */
    @ExcelProperty(value = "管段材质")
    @ApiModelProperty(value="管段材质")
    private String partMaterial;

    /**
     * 管段直径
     */
    @ExcelProperty(value = "管段直径")
    @ApiModelProperty(value="管段直径")
    private BigDecimal partRadius;

    /**
     * 检测方向
     */
    @ExcelProperty(value = "检测方向")
    @ApiModelProperty(value="检测方向")
    private String detectDir;

    /**
     * 管段长度
     */
    @ExcelProperty(value = "管段长度")
    @ApiModelProperty(value="管段长度")
    private BigDecimal partLen;

    /**
     * 检测长度
     */
    @ExcelProperty(value = "检测长度")
    @ApiModelProperty(value="检测长度")
    private BigDecimal detectLen;

    /**
     * 修养指数
     */
    @ExcelProperty(value = "修养指数")
    @ApiModelProperty(value="修养指数")
    private BigDecimal culIndex;

    /**
     * 养护指数
     */
    @ExcelProperty(value = "养护指数")
    @ApiModelProperty(value="养护指数")
    private BigDecimal mataIndex;

    /**
     * 检测地址
     */
    @ExcelProperty(value = "检测地址")
    @ApiModelProperty(value="检测地址")
    private String detectAddr;

    /**
     * 检测时间
     */
    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detectTime;


    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 照片
     */
    @ExcelProperty(value = "照片")
    @ApiModelProperty(value="照片")
    private String pic;

    /**
     * 检测单位
     */
    @ExcelProperty(value = "检测单位")
    @ApiModelProperty(value="检测单位")
    private String partUnti;

    /**
     *
     */
    @ExcelProperty(value = "检测人员")
    @ApiModelProperty(value="检测人员")
    private String partUser;

    /**
     * 核校人员
     */
    @ExcelProperty(value = "核校人员")
    @ApiModelProperty(value="核校人员")
    private String verifyUser;

    /**
     * 督促人员
     */
    @ExcelProperty(value = "督促人员")
    @ApiModelProperty(value="督促人员")
    private String spuUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间",example = "2019-02-23 02:23:36")
    @ExcelProperty(value = "修改时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ExcelProperty(value = "创建人")
    @ApiModelProperty(value="创建人")
    private String createUser;

    @ExcelProperty(value = "修改人")
    @ApiModelProperty(value="修改人")
    private String updateUser;


    /**
     * 工程id
     */
    @ExcelProperty(value = "工程id")
    @ApiModelProperty(value="工程id")
    @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;

    @ApiModelProperty("管段详细")
    private List<EngPipeTitleDetailDto> details;

    public EngPipeTitleDto() {

        details = new ArrayList<>();
    }

}
