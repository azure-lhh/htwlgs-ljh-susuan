package cn.htwlgs.guanwang.annotation;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 */
@Slf4j
@Component
@Aspect
public class PermissionInterceptor {

//    @Value("${userlogin.url:}")
    private   String userlogin_url = "http://172.21.92.68:18088/api/sso/auth/login";
    private   String userMenu_url = "http://172.21.92.68:18088/api/sso/auth/fegin/menu?systemFlag={systemFlag}&token={token}";

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    SsoFeginService ssoFeginService;


    /**
     * 定义切入点
     */
    @Pointcut("execution(public * cn.htwlgs.guanwang.controller.*.*(..)))")
    public void brokerAspect() {}


    /**
     * @description  在连接点执行之前执行的通知
     */
    @Before("brokerAspect()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
//        log.info("进入aop----");
//        // 获取方法上的注解
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        TransmissionRequired permission = signature.getMethod().getAnnotation(TransmissionRequired.class);
//
//        //获取RequestAttributes
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        //从获取RequestAttributes中获取HttpServletRequest的信息
//        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
////        Object userId = request.getAttribute(UserUtils.USER_ID);
////        Assert.notNull(userId, "当前用户未登录");
//
//
//        // 判断是否配置了注解和value
//        if(permission == null || !(StringUtils.hasLength(permission.value()))){
//            return ;
//        }
//        String perms = permission.value();
//
//        log.info("AOP -> perms -> {}", perms);
//        //请求user登录接口拿到token，在用token判断权限获取user信息
//
//
////        String token = body.getBody().getAccessToken();
//        String token= "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoxLCJ1c2VyX25hbWUiOiJhZG1pbiIsImV4cCI6MTU4OTM2MDU2OH0.g5SiM3s5tKZjIIT6_MMcIxh1rWgx1k4ZidOZWO8J64g";
//
//        // 判断当前用户的
//        // TODO 此处可用redis
////        UserMenuModel userMenuModel = userService.getUserMenu(Long.parseLong(userId.toString()), SystemFlag.SSO);
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userMenu_url, String.class,SystemFlag.GUAN_WANG,token);
//        String loginInfoDTO = responseEntity.getBody();
//
////        JsonResult<LoginModel> loginModel =  ssoFeginService.login("admin","admin");
////        JsonResult<UserMenuModel> userJson =  ssoFeginService.feginMenu(SystemFlag.MONITOR,token);
////        if (true) {
////            return JsonResult.success(loginInfoDTO);
////        }
//        JSONObject jsStr = JSONObject.parseObject(loginInfoDTO);
//        UserMenuModel userMenuModel = (UserMenuModel) JSONObject.toJavaObject(jsStr.getJSONObject("data"),UserMenuModel.class);
//       //Assert当 expression 不为 true 抛出异常；
////        Assert.isTrue(userMenuModel.getMenuArray() != null && userMenuModel.getMenuArray().contains(perms), "权限不足，无法访问");
////        return joinPoint.proceed();

    }




}
