package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName EngSewageInfoDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/6 15:00
 * @Version 1.0
 */
@Data
public class EngSewageInfoDto {

    @ApiModelProperty("检测单")
    private EngSewageTitleDto sewageTitle;
}
