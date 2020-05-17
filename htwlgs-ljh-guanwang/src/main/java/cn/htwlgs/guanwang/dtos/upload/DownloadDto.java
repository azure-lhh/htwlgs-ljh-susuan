package cn.htwlgs.guanwang.dtos.upload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DownloadDto {

    @ApiModelProperty(value = "下载文件名称",required = true)
    @NotEmpty(message = "下载文件名称 不能为空")
    private String fileName;
//
//    @ApiModelProperty(value = "下载文件保存地址",required = true)
//    @NotEmpty(message = "下载文件保存地址 不能为空")
//    private String filePath;

    @ApiModelProperty(value = "文件地址 多个地址用,号隔开",required = true)
    @NotEmpty(message = "文件地址 不能为空")
    private String fileUrls;
}
