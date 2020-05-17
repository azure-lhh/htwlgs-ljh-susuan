package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.SubEngInfoService;
import cn.htwlgs.guanwang.utils.PageList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@Api(tags = "子工程信息管理")
@RestController
@RequestMapping(value = "/subPro")
public class SubEngInfoController {

    @Autowired
    SubEngInfoService engInfoService;


    @ApiOperation(value = "获取子工程检查-缺陷管理信息列表")
    @RequestMapping(value = "/pageQuery",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:pageQuery")
    public JsonResult<PageList<EngInfoDto>> getEngInfoPaging(@RequestParam(value = "code") @ApiParam(value ="管段编号" ,required = true)  @NotBlank(message = "编码不能为空") String code,
                                                             @RequestParam(value = "type") @ApiParam(value ="子工程信息类别" ,required = true) Integer type,
                                                             @RequestParam(value = "engType") @ApiParam(value ="检查0或缺陷1",required = true) Integer engType,
                                                             @RequestParam(value = "page",defaultValue = "1") @ApiParam(value ="页码" ,required = true)  Integer pageNum,
                                                             @RequestParam(value = "size",defaultValue = "10") @ApiParam(value ="每页展示条数" ,required = true)  Integer pageSize){
        PageList<EngInfoDto> all = engInfoService.getEngInfoPaging(engType,code, type, pageNum, pageSize);
        return JsonResult.success(all);
    }

    @ApiOperation(value = "删除子工程信息")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:delete")
    public JsonResult deleteEngDetect(@RequestBody List<String> list){
        int count = engInfoService.deleteEngDetect(list);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "新增子工程信息")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:create")
    public JsonResult createSubEngDetect(@Validated  @RequestBody EngInfoDto dto){
        engInfoService.createSubEngDetect(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改子工程信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:update")
    public JsonResult updateSubEngDetect(@Validated(UpdateAttr.class) @RequestBody EngInfoDto dto){
         engInfoService.updateSubEngDetect(dto);
        return JsonResult.success();
    }



    @ApiOperation(value = "获取单个工程详细信息")
    @RequestMapping(value = "/subEng/info",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:subEng:info")
    public JsonResult<SubEngInfoDto> getPipeEngInfol(@RequestParam("id")  String id, @RequestParam("typeEnum") Integer typeEnum){
        SubEngInfoDto dto = engInfoService.querySubInfo(id, typeEnum);
        return JsonResult.success(dto);
    }


    @ApiOperation(value = "管网管段检测单--新增")
    @RequestMapping(value = "/pipeTitle/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:pipeTitle:create")
    public JsonResult createChecklist(@Validated  @RequestBody EngPipeTitleDto dto){
        engInfoService.createPipeCheckList(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "管网管段检测单--删除")
    @RequestMapping(value = "/pipeTitle/delete",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:pipeTitle:delete")
    public JsonResult deleteChecklist(@RequestParam("id") @ApiParam(value = "id",required = true) String id,@RequestParam("engId") @ApiParam(value = "engId",required = true) String engId){
        engInfoService.deletePipeChecklist(id,engId);
        return JsonResult.success();
    }

    @ApiOperation(value = "管网管段检测单--编辑")
    @RequestMapping(value = "/pipeTitle/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:pipeTitle:update")
    public JsonResult updateSubEngDetect(@Validated(UpdateAttr.class) @RequestBody EngPipeTitleDto dto){
         engInfoService.updateSubEngDetect(dto);
        return JsonResult.success();
    }



    @ApiOperation(value = "排污口检测单--新增")
    @RequestMapping(value = "/sewageTitle/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:sewageTitle:create")
    public JsonResult createSewageTitle(@Validated  @RequestBody EngSewageTitleDto dto){
        engInfoService.createSewageTitle(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "排污口检测单--编辑")
    @RequestMapping(value = "/sewageTitle/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:sewageTitle:update")
    public JsonResult updateSewageTitle(@Validated(UpdateAttr.class) @RequestBody EngSewageTitleDto dto){
        engInfoService.updateSewageTitle(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "排污口检测单--删除")
    @RequestMapping(value = "/sewageTitle/delete",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:sewageTitle:delete")
    public JsonResult deleteSewageTitle(@RequestParam("id") @ApiParam(value = "id",required = true) String id){
        engInfoService.deleteSewageTitle(id);
        return JsonResult.success();
    }



    @ApiOperation(value = "检查井检测单--新增")
    @RequestMapping(value = "/manholeTitle/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:manholeTitle:create")
    public JsonResult createManholeTitle(@Validated  @RequestBody EngManholeTitleDto dto){
        engInfoService.createManholeTitle(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "检查井检测单--编辑")
    @RequestMapping(value = "/manholeTitle/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:manholeTitle:update")
    public JsonResult updateManholeTitle(@Validated(UpdateAttr.class) @RequestBody EngManholeTitleDto dto){
         engInfoService.updateManholeTitle(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "检查井检测单--删除")
    @RequestMapping(value = "/manholeTitle/delete",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:manholeTitle:delete")
    public JsonResult deleteManholeTitle(@RequestParam("id") @ApiParam(value = "id",required = true) String id){
        engInfoService.deleteManholeTitle(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷详情--新增")
    @RequestMapping(value = "/subEngPipeDetect/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:subEngPipeDetect:create")
    public JsonResult createPipeDetect(@Validated  @RequestBody EngPipeDetectDto dto){
        engInfoService.createPipeDetect(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷详情--删除")
    @RequestMapping(value = "/subEngPipeDetect/delete",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:subEngPipeDetect:delete")
    public JsonResult deletePipeDetect(@RequestParam("id") @ApiParam(value = "id",required = true) String id){
        engInfoService.deletePipeDetect(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷详情--修改")
    @RequestMapping(value = "/subEngPipeDetect/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:subEngPipeDetect:update")
    public JsonResult updatePipeDetect(@Validated(UpdateAttr.class) @RequestBody EngPipeDetectDto dto){
         engInfoService.updatePipeDetect(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷评估--新增")
    @RequestMapping(value = "/subEngPipeEval/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:subEngPipeEval:create")
    public JsonResult createPipeEval(@Validated  @RequestBody EngPipeEvalDto dto){
        engInfoService.createPipeEval(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷评估--删除")
    @RequestMapping(value = "/subEngPipeEval/delete",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:subPro:subEngPipeEval:delete")
    public JsonResult deletePipeEval(@RequestParam("id") @ApiParam(value = "id",required = true) String id){
        engInfoService.deletePipeEval(id);
        return JsonResult.success();
    }

    @ApiOperation(value = "管段缺陷评估--修改")
    @RequestMapping(value = "/subEngPipeEval/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:subPro:subEngPipeEval:update")
    public JsonResult updatePipeEval(@Validated(UpdateAttr.class) @RequestBody EngPipeEvalDto dto){
        engInfoService.updatePipeEval(dto);
        return JsonResult.success();
    }


}
