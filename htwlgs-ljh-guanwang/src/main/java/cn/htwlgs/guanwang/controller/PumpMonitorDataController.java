package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.guanwang.dtos.PumpMonitorDataDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.PumpMonitorDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "物联网泵站监测--监测数据")
@RestController
@RequestMapping("/pump/data")
public class PumpMonitorDataController {
    
    @Autowired
    private PumpMonitorDataService service;

    @ApiOperation("添加")
    @PostMapping("/add")
    public JsonResult save(@Validated(InsertAttr.class) @RequestBody PumpMonitorDataDto dto){
        service.save(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody PumpMonitorDataDto dto){
        service.saveAndFlush(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    public JsonResult delete(@RequestBody List<Integer> idList){
        service.delete(idList);
        return JsonResult.success();
    }

    @ApiOperation(value = "数据导入 泵站监测数据")
    @PostMapping("/import/record")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.importExcel(file);
            return JsonResult.success("导入数据成功！");
        }catch (Exception e){
            log.error("泵站监测数据导入失败原因{}",e.getMessage());
            return JsonResult.error("泵站监测数据文件流 处理失败！"+e.getMessage());
        }
    }
}