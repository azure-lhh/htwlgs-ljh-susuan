package cn.htwlgs.guanwang.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsNumDto {

    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("数值")
    @JsonProperty("value")
    private BigDecimal num;
}
