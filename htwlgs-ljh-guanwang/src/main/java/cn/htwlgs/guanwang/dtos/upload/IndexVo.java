package cn.htwlgs.guanwang.dtos.upload;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IndexVo {

  /*  @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("图片列表")
    private List<Item> images;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("视频列表")
    private List<Item> videos;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("音频列表")
    private List<Item> audios;*/

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty("文件列表")
    private Item files;


    public IndexVo() {
        /*images = new ArrayList<>();
        videos = new ArrayList<>();
        audios = new ArrayList<>();*/
        files = new Item();
    }
}
