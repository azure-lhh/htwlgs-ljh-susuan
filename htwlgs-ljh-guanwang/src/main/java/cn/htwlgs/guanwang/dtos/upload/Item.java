package cn.htwlgs.guanwang.dtos.upload;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
public class Item {
    @ApiModelProperty(value="文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String name;

    @ApiModelProperty(value="文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String url;

    @ApiModelProperty(value="文件后缀")
    @NotBlank(message = "文件后缀不能为空")
    private String fileSuffix;

    @ApiModelProperty(value="上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime uploadTime;

}
