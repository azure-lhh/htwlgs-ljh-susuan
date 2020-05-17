package cn.htwlgs.guanwang.controller;


import cn.htwlgs.guanwang.dtos.PumpMonitorDto;
import cn.htwlgs.guanwang.dtos.PumpMonitorPageDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.PumpMonitorService;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Api(tags = "物联网泵站监测")
@RestController
@RequestMapping("/pump")
public class PumpMonitorController {
    
    @Autowired
    private PumpMonitorService service;

    @ApiOperation(value = "通过id查询 水位监测站信息")
    @GetMapping(value = "/query")
    public JsonResult<PumpMonitorDto> findById(@RequestParam(value = "stationId",required = false) @ApiParam("站点id") Integer stationId){
        return JsonResult.success(service.findById(stationId));
    }

    @ApiOperation("水位监测站-实时监测数据 列表")
    @GetMapping("/page")
    public JsonResult<PageList<PumpMonitorPageDto>> getPumpMonitorPageDto(@RequestParam(value = "name",required = false) @ApiParam("站点名称") String name,
                                                                           @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                                           @RequestParam(value = "date",required = false) @ApiParam("日期 yyyy-MM-dd") LocalDate date,
                                                                           @RequestParam(value = "time",required = false) @ApiParam("时间 HH:mm:ss") LocalTime time,
                                                                           @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                           @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPumpMonitorPageDto(name,area,date,time,pageNum,pageSize));
    }

    @ApiOperation("管网泵站监测列表")
    @GetMapping("/list")
    public JsonResult<PageList<PumpMonitorDto>> getPageList(@RequestParam(value = "name",required = false) @ApiParam("站点名称") String name,
                                                                @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                                @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(name,area,pageNum,pageSize));
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    public JsonResult save(@Validated(InsertAttr.class) @RequestBody PumpMonitorDto dto){
        service.save(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody PumpMonitorDto dto){
        service.saveAndFlush(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public JsonResult delete(@RequestBody List<Integer> idList){
        service.delete(idList);
        return JsonResult.success();
    }
}