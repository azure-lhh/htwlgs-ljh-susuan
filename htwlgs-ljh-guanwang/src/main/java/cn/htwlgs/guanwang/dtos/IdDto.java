package cn.htwlgs.guanwang.dtos;

import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class IdDto {

    @ApiModelProperty("id")
    @ExcelProperty(value = "序号",index = 0)
    @NotNull(message = "序号不能为空",groups = UpdateAttr.class)
    @Min(value = 1,groups = UpdateAttr.class)
    private Integer id;
}
