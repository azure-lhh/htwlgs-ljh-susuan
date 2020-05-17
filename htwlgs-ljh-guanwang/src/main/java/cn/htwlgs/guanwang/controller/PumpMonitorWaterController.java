package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.guanwang.dtos.PumpMonitorWaterDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.PumpMonitorWaterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "物联网泵站监测--水泵")
@RestController
@RequestMapping("/pump/water")
public class PumpMonitorWaterController {
    
    @Autowired
    private PumpMonitorWaterService service;

    @ApiOperation("添加")
    @PostMapping("/add")
    public JsonResult save(@Validated(InsertAttr.class) @RequestBody PumpMonitorWaterDto dto){
        service.save(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody PumpMonitorWaterDto dto){
        service.saveAndFlush(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public JsonResult delete(@RequestBody List<Integer> idList){
        service.delete(idList);
        return JsonResult.success();
    }

    /*@ApiOperation("管网泵站监测--水泵列表")
    @GetMapping("/list")
    public JsonResult<PageList<PumpMonitorWaterDto>> getPageList(@RequestParam(value = "stationId",required = false) @ApiParam("站点id") Integer stationId,
                                                                @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(stationId,pageNum,pageSize));
    }*/

    @ApiOperation("管网泵站监测--水泵列表")
    @GetMapping("/list")
    public JsonResult<List<PumpMonitorWaterDto>> getPageList(@RequestParam(value = "stationId",required = false) @ApiParam("站点id") Integer stationId){
        return JsonResult.success(service.getPageList(stationId));
    }
}