package cn.htwlgs.guanwang.controller;

import cn.htwlgs.guanwang.dtos.LevelMonitorDataDto;
import cn.htwlgs.guanwang.dtos.LevelMonitorDto;
import cn.htwlgs.guanwang.dtos.LevelMonitorPageDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.LevelMonitorService;
import cn.htwlgs.guanwang.utils.PageList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Api(tags = "物联网水位监测")
@RestController
@RequestMapping("/levelMonitor")
public class LevelMonitorController {

    @Autowired
    private LevelMonitorService service;

    @ApiOperation(value = "通过id查询 水位监测站信息")
    @GetMapping(value = "/query")
    public JsonResult<LevelMonitorDto> findById(@RequestParam(value = "stationId") @ApiParam(value = "站点id",required = true) Integer stationId){
        return JsonResult.success(service.findById(stationId));
    }

    @ApiOperation("水位监测站-实时监测数据 列表")
    @GetMapping("/page")
    public JsonResult<PageList<LevelMonitorPageDto>> getLevelMonitorPageDto(@RequestParam(value = "name",required = false) @ApiParam("站点名称") String name,
                                                                    @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                                            @RequestParam(value = "date",required = false) @ApiParam("日期 yyyy-MM-dd") LocalDate date,
                                                                            @RequestParam(value = "time",required = false) @ApiParam("时间 HH:mm:ss") LocalTime time,
                                                                    @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                    @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getLevelMonitorPageDto(name,area,date,time,pageNum,pageSize));
    }

    @ApiOperation("水位监测站列表")
    @GetMapping("/list")
    public JsonResult<PageList<LevelMonitorDto>> getPageByParam(@RequestParam(value = "name",required = false) @ApiParam("站点名称") String name,
                                                                @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                                @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(name,area,pageNum,pageSize));
    }

    @ApiOperation("添加 水位监测站")
    @PostMapping("/add")
    public JsonResult save(@Validated(InsertAttr.class) @RequestBody LevelMonitorDto dto){
        service.save(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改 水位监测站")
    @PostMapping("/modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody LevelMonitorDto dto){
        service.saveAndFlush(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除 水位监测站")
    @PostMapping("/remove")
    public JsonResult delete(@RequestBody List<Integer> idList){
        service.delete(idList);
        return JsonResult.success();
    }

    @ApiOperation("添加 水位监测站 监测数据")
    @PostMapping("/add/record")
    public JsonResult saveMonitorData(@Validated(InsertAttr.class) @RequestBody LevelMonitorDataDto dto){
        service.saveMonitorData(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改 水位监测站 监测数据")
    @PostMapping("/modify/record")
    public JsonResult modifyMonitorData(@Validated(UpdateAttr.class) @RequestBody LevelMonitorDataDto dto){
        service.saveAndFlushMonitorData(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除 水位监测站 监测数据")
    @PostMapping("/remove/record")
    public JsonResult deleteMonitorData(@RequestBody List<Integer> idList){
        service.deleteMonitorData(idList);
        return JsonResult.success();
    }

    @ApiOperation(value = "数据导入 监测数据")
    @PostMapping("/import/record")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.importExcel(file);
            return JsonResult.success("导入数据成功！");
        }catch (Exception e){
            log.error("监测数据导入失败原因{}",e.getMessage());
            return JsonResult.error("监测数据文件流 处理失败！"+e.getMessage());
        }
    }
}
