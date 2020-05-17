package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.InterceptingStatisticsDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.StorageTankDto;
import cn.htwlgs.guanwang.dtos.StorageTankListDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.StorageTankService;
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
@Api(tags = "调蓄池")
@RestController
@RequestMapping(value = "/storageTank")
public class StorageTankController {

    @Autowired
    private StorageTankService storageTankService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:storageTank:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(storageTankService.findAllId());
    }

    @ApiOperation("获取分页列表")
    @GetMapping("/list")
    @TransmissionRequired("guanwang:storageTank:list")
    public JsonResult<PageList<StorageTankListDto>> getPageList(@RequestParam(value = "name", required = false) @ApiParam("调蓄池名称") String name,
                                                                @RequestParam(value = "page", defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                @RequestParam(value = "size", defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize) {
        return JsonResult.success(storageTankService.getPageList(name, pageNum, pageSize));
    }

    @ApiOperation(value = "通过id查询调蓄池")
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @TransmissionRequired("guanwang:storageTank:query")
    public JsonResult<StorageTankListDto> findStorageTankById(@RequestParam("id") String id) {
        return JsonResult.success(storageTankService.findStorageTankById(id));
    }

    @ApiOperation(value = "新增调蓄池")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @TransmissionRequired("guanwang:storageTank:create")
    public JsonResult creat(@RequestBody @Validated(InsertAttr.class) StorageTankDto storageTankDto) {
        storageTankService.insert(storageTankDto);
        return JsonResult.success();

    }

    @ApiOperation("修改调蓄池")
    @PostMapping("/modify")
    @TransmissionRequired("guanwang:storageTank:modify")
    public JsonResult modify(@RequestBody @Validated(UpdateAttr.class) StorageTankDto dto) {
        storageTankService.modify(dto);
        return JsonResult.success();
    }

    @ApiOperation("批量删除")
    @PostMapping("/remove")
    @TransmissionRequired("guanwang:storageTank:remove")
    public JsonResult remove(@RequestBody List<Integer> stringList) {
       int count =  storageTankService.remove(stringList);
        return JsonResult.success(count);
    }


    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:storageTank:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            storageTankService.importExcel(file);
            return JsonResult.success("导入数据成功");
        } catch (Exception e) {
            log.error("数据导入失败原因{}", e.getMessage());
            return JsonResult.error("数据文件流 处理失败！");
        }
    }


    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:storageTank:export")
    public void exportExcel1(HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            storageTankService.exportExcel(response, "调蓄池数据", "storageTank", StorageTankDto.class, ExcelTypeEnum.XLSX,idList);
        } catch (Exception e) {
            log.error("调蓄池数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }

    @ApiOperation(value = "模板下载")
    @GetMapping("/modelDownload")
    @TransmissionRequired("guanwang:storageTank:modelDownload")
    public void modelDownload(HttpServletResponse response) {
        try {
            storageTankService.downloadModelExcel(response, "调蓄池导入模板", "调蓄池", StorageTankDto.class, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            log.error("调蓄池模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("调蓄池模板下载失败"),response);
        }

    }

    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/statistics/year")
    @TransmissionRequired("guanwang:storageTank:statistics:year")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(storageTankService.getSetupTimeList());
    }

    @ApiOperation(value = "统计 区域数量")
    @GetMapping(value = "/statistics/area")
    @TransmissionRequired("guanwang:storageTank:statistics:area")
    public JsonResult<List<StatisticsVarCherDto>> getAreaCode(){
        return JsonResult.success(storageTankService.getGroupByState());
    }

    @ApiOperation(value = "统计 内底标高，进流量，积水面积")
    @GetMapping(value = "/statistics/info")
    @TransmissionRequired("guanwang:storageTank:statistics:info")
    public JsonResult<InterceptingStatisticsDto> getStatisticsDto(){
        return JsonResult.success(storageTankService.getStatisticsDto());
    }
}
