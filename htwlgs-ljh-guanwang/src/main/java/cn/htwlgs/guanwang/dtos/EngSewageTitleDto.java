package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName EngSewageTitleDto
 * @Description  排水口Dto
 * @Author lihouhai
 * @Date 2020/4/30 11:29
 * @Version 1.0
 */
@Data
public class EngSewageTitleDto extends IdDto {



    @ApiModelProperty(value= "水体名称")
    private String wtbd;


    @ApiModelProperty(value= "河段")
    private String river;


    @ApiModelProperty(value= "调查地址")
    private String detectAddr;

    

    @ApiModelProperty(value = "调查时间",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime detecttime;


    @ApiModelProperty(value= "天气情况")
    private String weather;

      

    @ApiModelProperty(value= "调查人签字")
    private String detectUser;


    @ApiModelProperty(value= "调查单位")
    private String detectUnit;


    @ApiModelProperty(value= "排水口类型",example = "排放类型1")
    private String outfallType;


    @ApiModelProperty(value= "排水口编号")
    private String outfallCode;

      

    @ApiModelProperty(value= "排水口经度x")
    private BigDecimal lon;

      

    @ApiModelProperty(value= "排水口维度y")
    private BigDecimal lat;


    @ApiModelProperty(value= "排水口断面形式")
    private String sectionForm;

    @ApiModelProperty(value= "排水口尺寸大小")
    private Double sectionSize;


    @ApiModelProperty(value= "排水口材质")
    private String material;

    @ApiModelProperty(value= "出流形式")
    private String outfallWay;


    @ApiModelProperty(value= "管低高程")
    private String highLow;

    @ApiModelProperty(value= "末端控制")
    private String endControl;


    @ApiModelProperty(value= "水体水位")
    private String waterLevel;


    @ApiModelProperty(value= "编号")
    private String voucherCode;

      

    @ApiModelProperty(value= "旱天排水量")
    private BigDecimal droDrainage;


    @ApiModelProperty(value= "旱天CODcr")
    private BigDecimal droCodcr;


    @ApiModelProperty(value= "旱天氨氮")
    private BigDecimal droNh3n;

      
    @ApiModelProperty(value= "旱天总磷")
    private BigDecimal droP;

      
    @ApiModelProperty(value= "雨天排水量")
    private BigDecimal rainyDrainage;

      
    @ApiModelProperty(value= "雨天CODcr")
    private BigDecimal rainyCodcr;

      
    @ApiModelProperty(value= "雨天氨氮")
    private BigDecimal rainyNh3n;

      
    @ApiModelProperty(value= "雨天总磷")
    private BigDecimal rainyP;


    @ApiModelProperty(value= "图片编号")
    private String picCode;


    @ApiModelProperty(value= "工程信息id")
    @NotNull(message = "工程信息id不能为空",groups = {UpdateAttr.class, InsertAttr.class})
    private Integer engId;


    @ApiModelProperty(value = "创建时间",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @ApiModelProperty(value = "修改数据时间",example = "2019-02-23 02:23:36")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    @ApiModelProperty(value= "修改人")
    private String createUser;

    @ApiModelProperty(value= "创建人")
    private String updateUser;
}
