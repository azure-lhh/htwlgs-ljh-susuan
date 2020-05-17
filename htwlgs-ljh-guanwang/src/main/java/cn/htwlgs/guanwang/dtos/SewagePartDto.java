package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.enums.GWLevelEnum;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.SewagePart;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName SewagePartDto
 * @Description
 * @Author lihouhai
 * @Date 2020/4/23 9:11
 * @Version 1.0
 */
@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class SewagePartDto implements Serializable {
    private static final long serialVersionUID = 1L;
    

    @ApiModelProperty(value = "管网id")
    @ExcelProperty(value = "管网id")
    @NotNull(message = "序号不能为空",groups = UpdateAttr.class)
    @Min(value = 0)
    private Integer id;

    
    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    @NotBlank(message = "管段名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String pipeName;

    
    @ApiModelProperty(value = "管道编号")
    @ExcelProperty(value = "管道编号")
    @NotBlank(message = "管道编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String pipeNum;

    
    @ApiModelProperty(value = "所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    @NotBlank(message = "行政区划不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    /**
     * 管道级别
     */
    @ApiModelProperty(value = "管道级别")
    @ExcelProperty(value = "管道级别")
    private String level;

    
    @ApiModelProperty(value = "管道长度-Km")
    @ExcelProperty(value = "管道长度-Km")
    private BigDecimal length = BigDecimal.ZERO;

    
    @ApiModelProperty(value = "管道地址")
    @ExcelProperty(value = "管道地址")
    private String address;

    
    @ApiModelProperty(value = "经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    
    @ApiModelProperty(value = "纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    
    @ApiModelProperty(value = "管道空间拓扑关系")
    @ExcelProperty(value = "管道空间拓扑关系")
    private String relationship;

    
    @ApiModelProperty(value = "管道采集设备基础信息")
    @ExcelProperty(value = "管道采集设备基础信息")
    private String equipment;

    
    @ApiModelProperty(value = "管道形状（1圆形/2方形）")
    @ExcelProperty(value = "管道形状（1圆形/2方形）")
    private String shape;

    
    @ApiModelProperty(value = "管道内径-M")
    @ExcelProperty(value = "管道内径-M")
    private BigDecimal within = BigDecimal.ZERO;

    
    @ApiModelProperty(value = "管道外径-M")
    @ExcelProperty(value = "管道外径-M")
    private BigDecimal outside = BigDecimal.ZERO;

    
    @ApiModelProperty(value = "管道埋深-M")
    @ExcelProperty(value = "管道埋深-M")
    private BigDecimal depth = BigDecimal.ZERO;

    
    @ApiModelProperty(value = "出水偏移")
    @ExcelProperty(value = "出水偏移")
    private Integer effluent = 0;

    
    @ApiModelProperty(value = "入水偏移")
    @ExcelProperty(value = "入水偏移")
    private Integer enter = 0;

    
    @ApiModelProperty(value = "管道上游节点")
    @ExcelProperty(value = "管道上游节点")
    private String upperNode;

    
    @ApiModelProperty(value = "管道下游节点")
    @ExcelProperty(value = "管道下游节点")
    private String lowerNode;

    
    @ApiModelProperty(value = "上游底标高-M")
    @ExcelProperty(value = "上游底标高-M")
    private BigDecimal upperElevation = BigDecimal.ZERO;

    
    @ApiModelProperty(value = "下游底标高-M")
    @ExcelProperty(value = "下游底标高-M")
    private BigDecimal lowerElevation = BigDecimal.ZERO;

    @ColumnWidth(value = 18)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    @ExcelProperty(value = "创建时间",converter =CustomConverter.class)
    private LocalDateTime createTime;

    
    @ApiModelProperty(value = "水功能区编码")
    @ExcelProperty(value = "创建人")
    private String createUser;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty(value = "修改时间",converter =CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    
    @ApiModelProperty(value = "修改人")
    @ExcelProperty(value = "修改人")
    private String updateUser;

    
    @ApiModelProperty(value = "运营单位id")
    @ExcelProperty(value = "运营单位id")
    private String operateId;

    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;

    public SewagePartDto() {
    }

    public SewagePartDto(SewagePart sewagePart) {
        BeanUtils.copyProperties(sewagePart,this);
        this.level = sewagePart.getLevel().getVal();
        this.shape = sewagePart.getShape().getVal();
    }
}
