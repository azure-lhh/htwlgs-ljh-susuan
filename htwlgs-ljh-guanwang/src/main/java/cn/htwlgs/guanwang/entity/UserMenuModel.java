package cn.htwlgs.guanwang.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserMenuModel {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("用户类型（00系统用户）")
    private String userType;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty("头像路径")
    private String avatar;

    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty("权限菜单")
    private List<String> menuArray;



}
