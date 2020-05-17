package cn.htwlgs.guanwang.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginModel {

    @ApiModelProperty("授权用的access token")
    @JsonProperty("access_token")
    private String accessToken;

    @ApiModelProperty("用户名称")
    @JsonProperty("user_name")
    private String userName;
}