package cn.htwlgs.bigdata.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * @ClassName SectionResultDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/14 14:31
 * @Version 1.0
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
public class SectionResultDto {
    /**
     * 主要污染物
     */
    @ApiModelProperty(value = "主要评价指标名称")
    private  String keyPollutant;
    /**
     * 评价分级
     */
    @ApiModelProperty(value = "水质定性评价分级")
    private  Integer level;
    /**
     * 污染物指标均值浓度
     */
    @ApiModelProperty(value = "主要评价指标均值浓度")
    private Double  num;


    @ApiModelProperty(value = "首要污染物集")
    private List<SerialNoMonitorDto> list;


}
