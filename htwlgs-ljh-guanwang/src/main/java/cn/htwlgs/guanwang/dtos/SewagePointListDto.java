package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.entity.SewageSpot;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SewagePointListDto extends IdDto {


    /**
     * 管网名称
     */
    @ApiModelProperty(value = "名称")
    private String pipeName;

    /**
     * 管点编号
     */
    @ApiModelProperty(value = "管点编号")
    private String pipeNum;

    /**
     * 所属行政区划编码
     */
    @ApiModelProperty(value = "所属行政区划编码")
    private String areaCode;

    /**
     * 管点级别
     */
    @ApiModelProperty(value = "管点级别")
    private String level;

    /**
     * 管点长度-Km
     */
    @ApiModelProperty(value = "管点长度")
    private BigDecimal length = BigDecimal.ZERO;

    /**
     * 管点地址
     */
    @ApiModelProperty(value = "管点地址")
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal lon;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;

    /**
     * 管点空间拓扑关系
     */
    @ApiModelProperty(value = "管点空间拓扑关系")
    private String relationship;

    /**
     * 管点采集设备基础信息
     */
    @ApiModelProperty(value = "管点采集设备基础信息")
    private String equipment;

    /**
     * 管点形状（1圆形/2方形）
     */
    @ApiModelProperty(value = "管点形状")
    private String shape ;

    /**
     * 管点内径-M
     */
    @ApiModelProperty(value = "管点内径")
    private BigDecimal within = BigDecimal.ZERO;

    /**
     * 管点外径-M
     */
    @ApiModelProperty(value = "管点外径")
    private BigDecimal outside = BigDecimal.ZERO;

    /**
     * 管点埋深-M
     */
    @ApiModelProperty(value = "管点埋深")
    private BigDecimal depth = BigDecimal.ZERO;

    /**
     * 出水偏移
     */
    @ApiModelProperty(value = "出水偏移")
    private Integer effluent = 0;

    /**
     * 入水偏移
     */
    @ApiModelProperty(value = "入水偏移")
    private Integer enter = 0;

    /**
     * 管点上游节点
     */
    @ApiModelProperty(value = "上游节点")
    private String upperNode;

    /**
     * 管点下游节点
     */
    @ApiModelProperty(value = "下游节点")
    private String lowerNode;

    /**
     * 上游底标高-M
     */
    @ApiModelProperty(value = "上游底标高")
    private BigDecimal upperElevation = BigDecimal.ZERO;

    /**
     * 下游底标高-M
     */
    @ApiModelProperty(value = "下游底标高")
    private BigDecimal lowerElevation = BigDecimal.ZERO;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @ApiModelProperty(value = "修改人")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateUser;

    /**
     * 运营单位id
     */
    @ApiModelProperty(value = "运营单位id")
    private String operateId;


    @ApiModelProperty("建设时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime setupTime;

    public SewagePointListDto() {
    }

    public SewagePointListDto(SewageSpot item) {
        BeanUtils.copyProperties(item,this);
        this.level = item.getLevel().getVal();
        this.shape = item.getShape().getVal();
    }


}
