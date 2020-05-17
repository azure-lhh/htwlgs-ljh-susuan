package cn.htwlgs.guanwang.dtos;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName EngInfoPramaDto
 * @Description
 * @Author lihouhai
 * @Date 2020/5/6 14:19
 * @Version 1.0
 */
@Data
public class SubEngInfoDto {

    @ApiModelProperty(value=  "序号")
    private String id;

    @ApiModelProperty(value=  "类型")
    private String typeEnum;

    @ApiModelProperty("工程附件")
    private List<AttachmentDto> attachment;

    @ApiModelProperty(value=  "---排污口工程数据详情")
    private EngSewageTitleDto sewageInfo;


    @ApiModelProperty(value=  "---检查井工程数据详情")
    private EngManholeTitleDto manholeInfo;


    @ApiModelProperty(value=  "---管段工程数据数据详情")
    private EngPipeInfoDto pipeInfo;
}
