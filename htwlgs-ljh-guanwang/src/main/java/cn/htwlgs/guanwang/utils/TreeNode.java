package cn.htwlgs.guanwang.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Adminstrators
 * Date: 2019/10/15
 * Time: 18:46
 * Description: No Description
 */

//节点
@Data
public class TreeNode<T> {


    @ApiModelProperty(value = "id", notes = "id")
    @JSONField(name ="ID" )
    public String ID;

    @ApiModelProperty(value = "父ID", notes = "父ID")
    @JSONField(name ="SJID" )
    public Long SJID;

    @ApiModelProperty(value = "编号", notes = "编号")
    @JsonProperty("value")
    public Long BM;


    @ApiModelProperty(value = "子节点", notes = "子节点")
    //@JsonProperty("children")
    public List<T> children = new ArrayList<>();






}
