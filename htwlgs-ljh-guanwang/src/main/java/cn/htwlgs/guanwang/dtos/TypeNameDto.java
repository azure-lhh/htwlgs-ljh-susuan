package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName TypeNameDto
 * @Description
 * @Author lihouhai
 * @Date 2020/4/23 14:34
 * @Version 1.0
 */
@Data
public class TypeNameDto<T> {
    @ApiModelProperty(value="类型")
    private Integer  type;


    @ApiModelProperty(value = "0网管级别 1网管类型 2排污口排放类型 3排污口排放方式")
    private String name;


    @ApiModelProperty(value = "通过type取出相同类型列表")
    private List<T> list;



    public TypeNameDto() {
    }

    public TypeNameDto(Integer type,String name ,List<T> list) {
        this.name=name;
        this.type = type;
        this.list = list;
    }

    public TypeNameDto(String name) {
        this.name = name;
    }
}
