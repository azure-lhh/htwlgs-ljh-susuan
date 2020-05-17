package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.entity.SepticTank;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ContentRowHeight(15) // 内容高度
@HeadRowHeight(15)  //头行高
@ColumnWidth(15) //列宽
public class SepticTankDto extends IdDto{

    @ApiModelProperty(value = "化粪池名称",required = true)
    @ExcelProperty(value = "化粪池名称")
    @NotEmpty(message = "化粪池名称",groups = {InsertAttr.class, UpdateAttr.class})
    private String poolName;

    @ApiModelProperty("所属行政区划编码")
    @ExcelProperty(value = "所属行政区划编码")
    @NotBlank(message = "行政区划不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    @ApiModelProperty("地址")
    @ExcelProperty(value = "地址")
    private String address;

    @ApiModelProperty("化粪池编号")
    @ExcelProperty(value = "化粪池编号")
    @NotBlank(message = "化粪池编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String poolCode;

    @ApiModelProperty("标高-M")
    @ExcelProperty(value = "标高")
    private BigDecimal mark = BigDecimal.ZERO;

    @ApiModelProperty("规模")
    @ExcelProperty(value = "规模")
    private String scale;

    @ApiModelProperty("处理量")
    @ExcelProperty(value = "处理量")
    private BigDecimal handle = BigDecimal.ZERO;

    @ApiModelProperty("运行状态")
    @ExcelProperty(value = "运行状态")
    private String state;

    @ApiModelProperty("故障情况")
    @ExcelProperty(value = "故障情况")
    private String situation;

    @ApiModelProperty("经度")
    @ExcelProperty(value = "经度")
    private BigDecimal lon;

    @ApiModelProperty("纬度")
    @ExcelProperty(value = "纬度")
    private BigDecimal lat;

    @ApiModelProperty("创建时间")
    @ColumnWidth(value = 18)
    @ExcelProperty(value = "创建时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人")
    @ExcelProperty(value = "创建人")
    private String createUser;

    @ColumnWidth(value = 18)
    @ApiModelProperty("建设时间")
    @ExcelProperty(value = "建设时间",converter = CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;


    @ColumnWidth(value = 18)
    @ApiModelProperty(value = "修改时间")
    @ExcelProperty(value = "修改时间",converter =CustomConverter.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    public SepticTankDto() {
    }

    public SepticTankDto(SepticTank item) {
        BeanUtils.copyProperties(item,this);
        if(item.getState() == 1){
            this.setState(Constants.SEPTIC_TANK_STATE);
        }else{
            this.setState(Constants.SEPTIC_TANK_STATE_ON);
        }
    }
}
