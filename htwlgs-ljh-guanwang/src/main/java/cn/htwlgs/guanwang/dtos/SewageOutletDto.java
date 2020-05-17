package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.common.utils.CopyUtils;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.SewageOutlet;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName SewageOutletDto
 * @Description 排污口
 * @Author lihouhai
 * @Date 2020/4/20 13:21
 * @Version 1.0
 */
@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class SewageOutletDto {

    @ApiModelProperty(value = "序号")
    @ExcelProperty(value = "序号")
    @NotNull(message = "序号不能为空",groups = UpdateAttr.class)
    @Min(value = 0,groups = UpdateAttr.class)
    private Integer id;


    @ApiModelProperty(value = "排污口名称")
    @ExcelProperty(value = "排污口名称")
    @NotBlank(message = "排污口名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String name;

    @ApiModelProperty(value = "行政区划码")
    @ExcelProperty(value = "行政区划码不能为空")
    @NotBlank(message = "行政区划码不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    @ApiModelProperty(value = "排污口地址")
    @ExcelProperty(value = "排污口地址")
    @NotBlank(message = "排污口地址不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String  address;

    @ApiModelProperty(value = "经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    @ApiModelProperty(value = "纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ApiModelProperty(value = "检查井编号")
    @ExcelProperty(value = "检查井编号")
    private String serialNo;

    @ApiModelProperty(value = "井深")
    @ExcelProperty(value = "井深")
    private BigDecimal  depth;

    @ApiModelProperty(value = "排污类型1类型 2污水",example = "1")
    @ExcelProperty(value = "排污类型")
    private String  type;

    @ApiModelProperty(value = "规模")
    @ExcelProperty(value = "规模")
    private String  scale;

    @ApiModelProperty(value = "排放方式 1、")
    @ExcelProperty(value = "排放方式")
    private String  pfway;

    @ApiModelProperty(value = "类型")
    @ExcelProperty(value = "类型")
    private String  sewageType;


    @ApiModelProperty(value = "是否处于保护区 1、是 0 否")
    @ExcelProperty(value = "是否处于保护区")
    private boolean  reserves;



    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "修改时间",converter =CustomConverter.class)
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "修改人")
    @ExcelProperty(value = "类型")
    private String updateUser;


    @ApiModelProperty(value = "创建人")
    @ExcelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",converter =CustomConverter.class)
    private LocalDateTime createTime;


    @ApiModelProperty(value = "水功能区编码")
    @ExcelProperty(value = "水功能区编码")
    private String waterFunc;


    @ApiModelProperty(value = "水资源分区")
    @ExcelProperty(value = "水资源分区")
    private String waterRegion;


    @ApiModelProperty(value = "河湖编号")
    @ExcelProperty(value = "河湖编号")
    private String river;


    @ApiModelProperty(value = "存在问题")
    @ExcelProperty(value = "存在问题")
    private String remark;


    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;




    public SewageOutletDto() {
    }

    public SewageOutletDto(SewageOutlet sewageOutlet) {
        CopyUtils.copyProperties(sewageOutlet,this);
        this.type = sewageOutlet.getType().getVal();
        this.pfway = sewageOutlet.getPfway().getVal();
        this.sewageType = sewageOutlet.getSewageType().getVal();
    }



}
