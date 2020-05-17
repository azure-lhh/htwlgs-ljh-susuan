package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.InterceptingWellService;
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


@Api(tags = "截流井")
@Slf4j
@RestController
@RequestMapping(value = "/well")
public class InterceptingWellController {
    @Autowired
    InterceptingWellService wellService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:well:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(wellService.findAllId());
    }

    @ApiOperation("获取截流井分页列表")
    @GetMapping("/list")
    public JsonResult<PageList<InterceptingWellDto>> getPageList(@RequestParam(value = "name", required = false) @ApiParam("截流井名称") String name,
                                                                 @RequestParam(value = "page", defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                 @RequestParam(value = "size", defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize) {
        PageList<InterceptingWellDto> pageList = wellService.getPageList(name, pageNum, pageSize);
        return JsonResult.success(pageList);
    }

    @ApiOperation(value = "通过id查询截流井")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @TransmissionRequired("guanwang:well:query")
    public JsonResult<InterceptingWellDto> findwellById(@RequestParam("id") @ApiParam(value = "id",required = true) String id) {
        return JsonResult.success(wellService.findwellById(id));
    }

    @ApiOperation(value = "新增截流井")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @TransmissionRequired("guanwang:well:create")
    public JsonResult creat(@RequestBody @Validated(InsertAttr.class) InterceptingWellListDto interceptingWellListDto) {
        wellService.insert(interceptingWellListDto);
        return JsonResult.success();

    }

    @ApiOperation("修改")
    @PostMapping("/modify")
    @TransmissionRequired("guanwang:well:modify")
    public JsonResult modify(@RequestBody @Validated(UpdateAttr.class) InterceptingWellListDto dto) {
        wellService.modify(dto);
        return JsonResult.success();
    }

    @ApiOperation("批量删除")
    @PostMapping("/remove")
    @TransmissionRequired("guanwang:well:remove")
    public JsonResult remove(@RequestBody List<Integer> stringList) {
        int count =  wellService.remove(stringList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:well:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            wellService.importExcel(file);
        } catch (Exception e) {
            log.error("失败原因{}", e.getMessage());
            return JsonResult.error("文件流 处理失败！");
        }
        return JsonResult.success();
    }


    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:well:export")
    public void exportExcel1(HttpServletResponse response,@RequestBody List<Integer> idList)  {
        try {
            wellService.exportExcel(response, "截流井数据", "Sheet1", InterceptingWellListDto.class, ExcelTypeEnum.XLSX,idList);
        } catch (Exception e) {
            log.error("数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }
    }

    @ApiOperation(value = "模板下载")
    @GetMapping("/modelDownload")
    @TransmissionRequired("guanwang:well:modelDownload")

    public void modelDownload(HttpServletResponse response) {
        try {
            wellService.downloadModelExcel(response, "截流井导入模板", "Sheet1", InterceptingWellListDto.class, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            log.error("模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("模板下载失败"),response);
        }

    }


    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/statistics/year")
    @TransmissionRequired("guanwang:well:statistics:year")

    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(wellService.getSetupTimeList());
    }

    @ApiOperation(value = "统计 区域数量")
    @GetMapping(value = "/statistics/area")
    @TransmissionRequired("guanwang:well:statistics:area")

    public JsonResult<List<StatisticsVarCherDto>> getAreaCode(){
        return JsonResult.success(wellService.getGroupByState());
    }


    @ApiOperation(value = "统计 内底标高，进流量，积水面积")
    @GetMapping(value = "/statistics/info")
    @TransmissionRequired("guanwang:well:statistics:info")

    public JsonResult<InterceptingStatisticsDto> getStatisticsDto(){
        return JsonResult.success(wellService.getStatisticsDto());
    }
}
