package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class SewagePointDto {
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private Integer id;

    /**
     * 管网名称
     */
    @ApiModelProperty(value = "管网名称")
    @ExcelProperty(value = "管网名称")
    @NotBlank(message = "管网名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String pipeName;

    /**
     * 管点编号
     */
    @ApiModelProperty(value = "管点编号")
    @ExcelProperty(value = "管点编号")
    @NotBlank(message = "管点编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String pipeNum;

    /**
     * 所属行政区划编码
     */
    @ApiModelProperty(value = "所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    @NotBlank(message = "行政区划不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    /**
     * 管点级别
     */
    @ApiModelProperty(value = "管点级别")
    @ExcelProperty(value = "管点级别")
    private String level;

    /**
     * 管点长度-Km
     */
    @ApiModelProperty(value = "管点长度")
    @ExcelProperty(value = "管点长度")
    private BigDecimal length = BigDecimal.ZERO;

    /**
     * 管点地址
     */
    @ApiModelProperty(value = "管点地址")
    @ExcelProperty(value = "管点地址")
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    /**
     * 管点空间拓扑关系
     */
    @ApiModelProperty(value = "管点空间拓扑关系")
    @ExcelProperty(value = "管点空间拓扑关系")
    private String relationship;

    /**
     * 管点采集设备基础信息
     */
    @ApiModelProperty(value = "管点采集设备基础信息")
    @ExcelProperty(value = "管点采集设备基础信息")
    private String equipment;

    /**
     * 管点形状（1圆形/2方形）
     */
    @ApiModelProperty(value = "管点形状")
    @ExcelProperty(value = "管点形状")
    private String shape;

    /**
     * 管点内径-M
     */
    @ApiModelProperty(value = "管点内径")
    @ExcelProperty(value = "管点内径")
    private BigDecimal within = BigDecimal.ZERO;

    /**
     * 管点外径-M
     */
    @ApiModelProperty(value = "管点外径")
    @ExcelProperty(value = "管点外径")
    private BigDecimal outside = BigDecimal.ZERO;

    /**
     * 管点埋深-M
     */
    @ApiModelProperty(value = "管点埋深")
    @ExcelProperty(value = "管点埋深")
    private BigDecimal depth = BigDecimal.ZERO;

    /**
     * 出水偏移
     */
    @ApiModelProperty(value = "出水偏移")
    @ExcelProperty(value = "出水偏移")
    private Integer effluent = 0;

    /**
     * 入水偏移
     */
    @ApiModelProperty(value = "入水偏移")
    @ExcelProperty(value = "入水偏移")
    private Integer enter = 0;

    /**
     * 管点上游节点
     */
    @ApiModelProperty(value = "管点上游节点")
    @ExcelProperty(value = "管点上游节点")
    private String upperNode;

    /**
     * 管点下游节点
     */
    @ApiModelProperty(value = "管点下游节点")
    @ExcelProperty(value = "管点下游节点")
    private String lowerNode;

    /**
     * 上游底标高-M
     */
    @ApiModelProperty(value = "上游底标高")
    @ExcelProperty(value = "上游底标高")
    private BigDecimal upperElevation = BigDecimal.ZERO;

    /**
     * 下游底标高-M
     */
    @ApiModelProperty(value = "下游底标高")
    @ExcelProperty(value = "下游底标高")
    private BigDecimal lowerElevation = BigDecimal.ZERO;



    /**
     * 运营单位id
     */
    @ApiModelProperty(value = "运营单位id")
    @ExcelProperty(value = "运营单位id")
    private String operateId;


    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;




}
