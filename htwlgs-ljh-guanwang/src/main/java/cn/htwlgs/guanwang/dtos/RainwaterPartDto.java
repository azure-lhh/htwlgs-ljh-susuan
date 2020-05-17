package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.RainwaterPart;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class RainwaterPartDto extends IdDto{

    @ApiModelProperty(value = "雨水管段名称",required = true)
    @ExcelProperty(value = "雨水管段名称")
    @NotEmpty(message = "雨水管段名称",groups = {InsertAttr.class, UpdateAttr.class})
    private String pipeName;

    @ApiModelProperty("雨水管段编号")
    @ExcelProperty(value = "雨水管段编号")
    @NotEmpty(message = "雨水管段名称不能为空")
    private String pipeNum;

    @ApiModelProperty("所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    private String areaCode;

    @ApiModelProperty(value = "雨水管段级别",example = "一级")
    @ExcelProperty(value = "雨水管段级别")
    private String level;

    @ApiModelProperty("雨水管段长度")
    @ExcelProperty(value = "雨水管段长度")
    private BigDecimal length = BigDecimal.ZERO;

    @ApiModelProperty("雨水管段地址")
    @ExcelProperty(value = "雨水管段地址")
    private String address;

    @ApiModelProperty("经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ApiModelProperty("雨水管段空间拓扑关系")
    @ExcelProperty(value = "雨水管段空间拓扑关系")
    private String relationship;

    @ApiModelProperty("雨水管段采集设备基础信息")
    @ExcelProperty(value = "雨水管段采集设备基础信息")
    private String equipment;

    @ApiModelProperty("雨水管段形状")
    @ExcelProperty(value = "雨水管段形状")
    private String shape;

    @ApiModelProperty("雨水管段内径")
    @ExcelProperty(value = "雨水管段内径")
    private BigDecimal within = BigDecimal.ZERO;

    @ApiModelProperty("雨水管段外径")
    @ExcelProperty(value = "雨水管段外径")
    private BigDecimal outside = BigDecimal.ZERO;

    @ApiModelProperty("雨水管段埋深")
    @ExcelProperty(value = "雨水管段埋深")
    private BigDecimal depth = BigDecimal.ZERO;

    @ApiModelProperty("出水偏移")
    @ExcelProperty(value = "出水偏移")
    private Integer effluent = 0;

    @ApiModelProperty("入水偏移")
    @ExcelProperty(value = "入水偏移")
    private Integer enter = 0;

    @ApiModelProperty("雨水管段上游节点")
    @ExcelProperty(value = "雨水管段上游节点")
    private String upperNode;

    @ApiModelProperty("雨水管段下游节点")
    @ExcelProperty(value = "雨水管段下游节点")
    private String lowerNode;

    @ApiModelProperty("上游底标高")
    @ExcelProperty(value = "上游底标高")
    private BigDecimal upperElevation = BigDecimal.ZERO;

    @ApiModelProperty("下游底标高")
    @ExcelProperty(value = "下游底标高")
    private BigDecimal lowerElevation = BigDecimal.ZERO;

    @ApiModelProperty("创建时间")
    @ColumnWidth(value = 18)
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @ExcelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty("运营单位id")
    @ExcelProperty(value = "运营单位id")
    private String operateId;

    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;




    public RainwaterPartDto() {
    }

    public RainwaterPartDto(RainwaterPart item) {
        BeanUtils.copyProperties(item,this);
        this.setShape(item.getShape().getVal());
        this.setLevel(item.getLevel().getVal());
    }
}
