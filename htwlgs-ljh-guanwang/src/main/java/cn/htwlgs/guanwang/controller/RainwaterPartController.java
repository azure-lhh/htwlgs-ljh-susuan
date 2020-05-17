package cn.htwlgs.guanwang.controller;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.DataDto;
import cn.htwlgs.guanwang.dtos.RainwaterPartDto;
import cn.htwlgs.guanwang.dtos.RainwaterStatisticsDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.RainwaterPartService;
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
@Api(tags = "雨水管段")
@RestController
@RequestMapping(value = "/rainwater/part")
public class RainwaterPartController {

    @Autowired
    private RainwaterPartService service;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:rainwater:part:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(service.findAllId());
    }

    @ApiOperation(value = "通过id查询")
    @GetMapping(value = "/query")
    @TransmissionRequired("guanwang:rainwater:part:query")
    public JsonResult<RainwaterPartDto> findById(@RequestParam @ApiParam(value = "id",required = true) @NotBlank Integer id){
        return JsonResult.success(service.findById(id));
    }

    @ApiOperation("获取分页列表")
    @GetMapping("/list")
    @TransmissionRequired("guanwang:rainwater:part:list")
    public JsonResult<PageList<RainwaterPartDto>> getPageList(@RequestParam(value = "name",required = false) @ApiParam("雨水管段名称") String name,
                                                               @RequestParam(value = "area",required = false) @ApiParam("所属行政区划编码") String area,
                                                               @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                               @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(service.getPageList(name,area,pageNum,pageSize));
    }

    @ApiOperation("添加")
    @PostMapping("/add")
    @TransmissionRequired("guanwang:rainwater:part:add")
    public JsonResult add(@Validated(InsertAttr.class) @RequestBody RainwaterPartDto dto){
        service.add(dto);
        return JsonResult.success();
    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    @TransmissionRequired("guanwang:rainwater:part:modify")
    public JsonResult modify(@Validated(UpdateAttr.class) @RequestBody RainwaterPartDto dto){
        service.modify(dto);
        return JsonResult.success();
    }

    @ApiOperation("删除")
    @PostMapping("/remove")
    @TransmissionRequired("guanwang:rainwater:part:remove")
    public JsonResult remove(@RequestBody List<String> idList){
        service.remove(idList);
        return JsonResult.success();
    }



    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:rainwater:part:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            service.importExcel(file);
            return JsonResult.success("导入数据成功！");
        }catch (Exception e){
            log.error("雨水管段数据导入失败原因{}",e.getMessage());
            return JsonResult.error("雨水管段数据文件流 处理失败！"+e.getMessage());
        }
    }

    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:rainwater:part:export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            service.exportExcel(response, "数据导出", "雨水管段数据表", RainwaterPartDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("雨水管段数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }

    @ApiOperation(value = "模板下载")
    @GetMapping("/download")
    @TransmissionRequired("guanwang:rainwater:part:download")
    public void downloadExcel (HttpServletResponse response) {
        try {
            service.downloadExcel(response, "雨水管段导入模板", "雨水管段导入模板", RainwaterPartDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("雨水管段模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("雨水管段模板下载失败"),response);
        }

    }

    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/statistics/year")
    @TransmissionRequired("guanwang:rainwater:part:statistics:year")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(service.getSetupTimeList());
    }

    @ApiOperation(value = "统计 区域数量")
    @GetMapping(value = "/statistics/area")
    @TransmissionRequired("guanwang:rainwater:part:statistics:area")
    public JsonResult<List<StatisticsVarCherDto>> getGroupByState(){
        return JsonResult.success(service.getGroupByState());
    }

    @ApiOperation(value = "统计 管网总长，管道埋深")
    @GetMapping(value = "/statistics/length")
    @TransmissionRequired("guanwang:rainwater:part:statistics:length")
    public JsonResult<RainwaterStatisticsDto> getStatisticsDto(){
        return JsonResult.success(service.getStatisticsDto());
    }

    @ApiOperation(value = "统计 内外径半径")
    @GetMapping(value = "/radiisState")
    @TransmissionRequired("guanwang:rainwater:part:radiisState")
    public JsonResult<List<DataDto>> getPartRadiis(){
        return JsonResult.success(service.getPartRadiis());
    }

    @ApiOperation(value = "统计 管道级别")
    @GetMapping(value = "/statistics/leave")
    @TransmissionRequired("guanwang:rainwater:part:statistics:leave")
    public JsonResult<List<StatisticsVarCherDto>> getLeaveList(){
        return JsonResult.success(service.getLeaveList());
    }
}
