package cn.htwlgs.guanwang.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class SurpassmFile {


    @ApiModelProperty(value="文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty(value="文件后缀")
    @NotBlank(message = "文件后缀不能为空")
    private String fileSuffix;

    @ApiModelProperty(value="文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String url;

}
