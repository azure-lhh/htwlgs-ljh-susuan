package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InterceptingWellDto  extends IdDto {


    @ApiModelProperty(value = "截流井名称")
    @NotBlank(message = "截流井名称不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String wellName;


    @ApiModelProperty(value = "所属行政区划编码")
    @NotBlank(message = "行政区划不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String areaCode;

    @ApiModelProperty(value = "地址")
    @NotBlank(message = "地址不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String address;


    @ApiModelProperty(value = "截流井编号")
    @NotBlank(message = "编号不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private String wellCode;


    @ApiModelProperty(value = "内底标高")
    private BigDecimal insoleElevation;


    @ApiModelProperty(value = "进流量")
    private BigDecimal inflow;


    @ApiModelProperty(value = "积水面积")
    private BigDecimal catchmentArea;


    @ApiModelProperty(value = "初始深度")
    private BigDecimal initialDepth;


    @ApiModelProperty(value = "最大深度")
    private BigDecimal maxDepth;


    @ApiModelProperty(value = "超载深度")
    private BigDecimal overloadDepth;


    @ApiModelProperty(value = "经度")
    private BigDecimal lon;


    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "创建人")
    private String createUser;


    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @ApiModelProperty(value = "修改人")
    private String updateUser;


    @ApiModelProperty("建设时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;

}
