package cn.htwlgs.guanwang.controller;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.RainwaterSpotDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.RainwaterSpotService;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "雨水管点")
@RestController
@RequestMapping(value = "/rainwater/spot")
public class RainwaterSpotController {

    @Autowired
    private RainwaterSpotService service;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:rainwater:spot:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(service.findAllId());
    }

    @ApiOperation(value = "通过id查询")
    @GetMapping(value = "/query")
    @TransmissionRequired("guanwang:rainwater:spot:query")
    public JsonResult<RainwaterSpotDto> findById(@RequestParam @ApiParam(value = "id",required = true) @NotBlank Integer id){
        return JsonResult.success(service.findById(id));
    }

    @ApiOperation("获取分页列表")
    @GetMapping("/list")
    @TransmissionRequired("guanwang:rainwater:spot:list")
    public JsonResult<PageList<RainwaterSpotDto>> getPageList(@RequestParam(value = "name",required = false) @ApiParam("雨水管点名称") String name,
                                                              @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                              @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                              @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(name,area,pageNum,pageSize));
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    @TransmissionRequired("guanwang:rainwater:spot:add")
    public JsonResult add(@Validated(InsertAttr.class) @RequestBody RainwaterSpotDto dto){
        service.add(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    @TransmissionRequired("guanwang:rainwater:spot:modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody RainwaterSpotDto dto){
        service.modify(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    @TransmissionRequired("guanwang:rainwater:spot:remove")
    public JsonResult remove(@RequestBody List<String> idList){
        service.remove(idList);
        return JsonResult.success();
    }

    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:rainwater:spot:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.importExcel(file);
            return JsonResult.success("导入数据成功！");
        }catch (Exception e){
            log.error("雨水管点数据导入失败原因{}",e.getMessage());
            return JsonResult.error("雨水管点数据文件流 处理失败！"+e.getMessage());
        }
    }

    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:rainwater:spot:export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            service.exportExcel(response, "数据导出", "雨水管点数据表", RainwaterSpotDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("雨水管点数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }

    @ApiOperation(value = "模板下载")
    @GetMapping("/download")
    @TransmissionRequired("guanwang:rainwater:spot:download")
    public void downloadExcel (HttpServletResponse response) {
        try {
            service.downloadExcel(response, "雨水管点导入模板", "雨水管点导入模板", RainwaterSpotDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("雨水管点模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("雨水管点模板下载失败"),response);
        }

    }
}
