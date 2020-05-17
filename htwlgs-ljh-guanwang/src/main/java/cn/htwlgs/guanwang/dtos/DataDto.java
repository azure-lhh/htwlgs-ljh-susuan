package cn.htwlgs.guanwang.dtos;

import lombok.Data;

import java.util.List;

/**
 * @ClassName DataDto
 * @Description
 * @Author lihouhai
 * @Date 2020/4/28 14:47
 * @Version 1.0
 */
@Data
public class DataDto<T> {
    private  String name;
    private List<T>  list;

    public DataDto(String name, List<T> list) {
        this.name = name;
        this.list = list;
    }

    public DataDto() {
    }
}
