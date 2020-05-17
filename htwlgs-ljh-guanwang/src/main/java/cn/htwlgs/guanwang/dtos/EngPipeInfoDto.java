package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class EngPipeInfoDto  {

    @ApiModelProperty("检测单")
    private EngPipeTitleDto pipeTitle;

    @ApiModelProperty("缺陷详情")
    private EngPipeDetectDto pipeDetect;

    @ApiModelProperty("缺陷评估")
    private EngPipeEvalDto pipeEval;

    public EngPipeInfoDto() {
    }

    public EngPipeInfoDto(EngPipeTitleDto pipeTitle, EngPipeDetectDto pipeDetect, EngPipeEvalDto pipeEval) {
        this.pipeTitle = pipeTitle;
        this.pipeDetect = pipeDetect;
        this.pipeEval = pipeEval;
    }
}
