package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.PumpingStationDto;
import cn.htwlgs.guanwang.dtos.PumpingStationStatisticsDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.PumpingStationService;
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
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "管网泵站")
@RestController
@RequestMapping("/station")
public class PumpingStationController {

    @Autowired
    private PumpingStationService service;

    @ApiOperation(value = "通过id查询")
    @GetMapping(value = "/query")
    @TransmissionRequired("guanwang:station:query")
    public JsonResult<PumpingStationDto> findById(@RequestParam @ApiParam(value = "id",required = true) String id){
        return JsonResult.success(service.findById(id));
    }

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:station:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(service.findAllId());
    }

    @ApiOperation("获取分页列表")
    @GetMapping("/list")
    @TransmissionRequired("guanwang:station:list")
    public JsonResult<PageList<PumpingStationDto>> getPageList(@RequestParam(value = "name",required = false) @ApiParam("泵站名称") String name,
                                                               @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                               @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(name,pageNum,pageSize));
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    @TransmissionRequired("guanwang:station:add")
    public JsonResult add(@Validated(InsertAttr.class) @RequestBody PumpingStationDto dto){
        service.add(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    @TransmissionRequired("guanwang:station:modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody PumpingStationDto dto){
        service.modify(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    @TransmissionRequired("guanwang:station:remove")
    public JsonResult remove(@RequestBody List<String> idList){
        service.remove(idList);
        return JsonResult.success();
    }

    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:station:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.importExcel(file);
            return JsonResult.success("导入数据成功！");
        }catch (Exception e){
            log.error("管网泵站数据导入失败原因{}",e.getMessage());
            return JsonResult.error("管网泵站数据文件流 处理失败！"+e.getMessage());
        }
    }

    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:station:export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            service.exportExcel(response, "数据导出", "管网泵站数据表", PumpingStationDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("管网泵站数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }

    @ApiOperation(value = "模板下载")
    @GetMapping("/download")
    @TransmissionRequired("guanwang:station:download")
    public void downloadExcel (HttpServletResponse response) {
        try {
            service.downloadExcel(response, "泵站导入模板", "管网泵站导入模板", PumpingStationDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("管网泵站模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("管网泵站模板下载失败"),response);
        }
    }

    @ApiOperation(value = "统计 水位，流量，开关")
    @GetMapping(value = "/statistics/wl")
    @TransmissionRequired("guanwang:station:statistics:wl")
    public JsonResult<PumpingStationStatisticsDto> getStatisticsDto(){
        return JsonResult.success(service.getStatisticsDto());
    }

    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/statistics/year")
    @TransmissionRequired("guanwang:station:statistics:year")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(service.getSetupTimeList());
    }

    @ApiOperation(value = "统计 区域数量")
    @GetMapping(value = "/statistics/area")
    @TransmissionRequired("guanwang:station:statistics:area")
    public JsonResult<List<StatisticsVarCherDto>> getGroupByState(){
        return JsonResult.success(service.getGroupByState());
    }
}
