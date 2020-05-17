package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.annotation.SsoFeginService;
import cn.htwlgs.guanwang.annotation.SystemFlag;
import cn.htwlgs.guanwang.configuration.ListenerConfig;
import cn.htwlgs.guanwang.dtos.TypeNameDto;
import cn.htwlgs.guanwang.entity.UserMenuModel;
import cn.htwlgs.guanwang.service.DataService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Api(tags = "公用接口")
@RestController
@RequestMapping(value = "/common")
public class CommonController {


    @Autowired
    DataService dataService;
    @Autowired
    SsoFeginService ssoFeginService;
    @Autowired
    RestTemplate restTemplate;

    @ApiOperation(value = "获取数据类型")
    @RequestMapping(value = "/typeName",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:common:typeName")
    public JsonResult<List<TypeNameDto<String>>> getTypeName(){
        return JsonResult.success(ListenerConfig.typeNameList);
    }

    @ApiOperation(value = "获取行政区域树")
    @RequestMapping(value = "/getAreaTree",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:common:getAreaTree")
    public JsonResult getAreaTree(){
        return JsonResult.success(ListenerConfig.adminRegionTree);
    }



    @ApiOperation(value = "获取所有管段编号")
    @RequestMapping(value = "/pipeCodes",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:common:pipeCodes")
    public JsonResult<List<String>> findAllPipeNum(){
        return JsonResult.success(dataService.findAllPipeNum());
    }

    @ApiOperation(value = "获取用户菜单")
    @RequestMapping(value = "/getMenu",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:common:getMenu")
    public JsonResult<UserMenuModel> getUserMenu(String token){
        String userMenu_url = "http://172.21.92.68:18088/api/sso/auth/fegin/menu?systemFlag={systemFlag}&token={token}";

//        JsonResult<UserMenuModel> userJsonDto =  ssoFeginService.feginMenu(SystemFlag.MONITOR,token); //fegin 正常用这个获取
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(userMenu_url, String.class, SystemFlag.GUAN_WANG,token);
        String userJsonDto = responseEntity.getBody();
        JSONObject jsStr = JSONObject.parseObject(userJsonDto);
        UserMenuModel userMenuModel = (UserMenuModel) JSONObject.toJavaObject(jsStr.getJSONObject("data"),UserMenuModel.class);

        return JsonResult.success(userMenuModel);
    }
}
