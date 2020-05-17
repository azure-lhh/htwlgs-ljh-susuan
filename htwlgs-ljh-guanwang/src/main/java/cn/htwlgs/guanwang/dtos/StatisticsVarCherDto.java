package cn.htwlgs.guanwang.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticsVarCherDto {
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("数值")
    @JsonProperty("value")
    private BigDecimal num = BigDecimal.ZERO;

    public StatisticsVarCherDto(){

    }

    public StatisticsVarCherDto(String name, BigDecimal num){
        this.setName(name);
        this.setNum(num);
    }
}
