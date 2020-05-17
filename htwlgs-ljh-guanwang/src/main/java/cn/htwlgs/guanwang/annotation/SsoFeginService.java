package cn.htwlgs.guanwang.annotation;


import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.entity.LoginModel;
import cn.htwlgs.common.entity.UserMenuModel;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "htwlgs-sso")
public interface SsoFeginService {

    @GetMapping("/auth/fegin/menu")
    JsonResult<UserMenuModel> feginMenu(@RequestParam(name = "systemFlag", defaultValue = "") String systemFlag,
                                        @RequestParam(name = "token", defaultValue = "") String token);

    @PostMapping("/auth/login")
    JsonResult<LoginModel> login(@RequestParam(name = "username", defaultValue = "") @ApiParam("用户名") String username,
                                 @RequestParam(name = "password", defaultValue = "") @ApiParam("密码") String password);
}
