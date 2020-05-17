package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.utils.TreeNode;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName AdminRegion
 * @Description 行政区划
 * @Author lihouhai
 * @Date 2020/4/26 9:22
 * @Version 1.0
 */
@Data
public class AdminRegionDto extends TreeNode {

    /**
     * CC : 4
     * ZXWD : null
     * XZDJ : 县级
     * PX : 23
     * BM : 500118003000
     * MCQC : null
     * XZDJDM : 4
     * GMT_CREATE : 2019-12-26T02:41:04.000+0000
     * ZXJD : null
     * SJID : 500118
     * MC : 南大街街道
     * GMT_MODIFIED : 2019-12-26T02:41:04.000+0000
     * ID : 09c0f33a-4707-4a00-b59b-ccd3c6f33372
     * LJ : 中国\重庆市\永川区\南大街街道
     * MCSX : null
     */



    @JSONField(name ="ZXWD" )
    private Object ZXWD;

    @ApiModelProperty(value = "级别",notes = "子节点")
    @JSONField(name ="XZDJ" )
    private String XZDJ;

    @JSONField(name ="PX" )
    private long PX;

    @ApiModelProperty(value = "名称")
    @JSONField(name ="MCQC" )
    private Object MCQC;

    @ApiModelProperty(value = "级别编号", notes = "级别编号")
    @JSONField(name ="XZDJDM" )
    private long XZDJDM;

    @ApiModelProperty(value = "创建时间", notes = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name ="ZXWD" )
    private LocalDateTime GMT_CREATE;


    @JSONField(name ="ZXJD" )
    private Object ZXJD;

    @JSONField(name ="MC" )
    @JsonProperty("label")
    private String MC;

    @ApiModelProperty(value = "修改时间", notes = "修改时间")
    @JSONField(name ="GMT_MODIFIED" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String GMT_MODIFIED;

    @ApiModelProperty(value = "全称", notes = "全称")
    @JSONField(name ="LJ" )
    private String LJ;

    @JSONField(name ="MCSX" )
    private Object MCSX;




}
