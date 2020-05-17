package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.OperateOrgDto;
import cn.htwlgs.guanwang.service.OperateOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import javax.validation.constraints.Min;

/**
 * @ClassName MangeOrgController
 * @Description
 * @Author lihouhai
 * @Date 2020/5/10 15:43
 * @Version 1.0
 */
@Slf4j
@Api(tags = "运营单位")
@RestController
@RequestMapping(value = "/mangeOrg")
public class MangeOrgController {
    @Autowired
    OperateOrgService operateOrgService;

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:mangeOrg:query")
    public JsonResult<OperateOrgDto> findById(@RequestParam @ApiParam(value = "id序号",required = true) @Min(value = 0,message = "序号id错误") Integer id){
        OperateOrgDto byId = operateOrgService.findById(id);
        return JsonResult.success(byId);
    }
}
