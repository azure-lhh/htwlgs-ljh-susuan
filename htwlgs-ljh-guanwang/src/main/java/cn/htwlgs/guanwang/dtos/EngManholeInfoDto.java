package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName EngManholeInfoDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/6 15:00
 * @Version 1.0
 */
@Data
public class EngManholeInfoDto {
    @ApiModelProperty("检测单")
    private EngManholeTitleDto manholeTitle;


}
