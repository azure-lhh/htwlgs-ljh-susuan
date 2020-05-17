package cn.htwlgs.guanwang.controller;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.DataDto;
import cn.htwlgs.guanwang.dtos.RainwaterStatisticsDto;
import cn.htwlgs.guanwang.dtos.SewagePartDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.SewagePartService;
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
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName SewagePartController
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 17:51
 * @Version 1.0
 */
@Slf4j
@Api(tags = "污水管段")
@RestController
@RequestMapping(value = "/sewagePart")
public class SewagePartController {

    @Autowired
    private SewagePartService sewagePartService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:sewagePart:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(sewagePartService.findAllId());
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    @TransmissionRequired("guanwang:sewagePart:pageQuery")
    public JsonResult queryPageAllByName(@RequestParam(value = "keyWord",required = false) @ApiParam("管段名称") String keyWord,
                                         @RequestParam(value = "areaCode",required = false) @ApiParam("所属行政区划编码") String areaCode,
                                         @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                         @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        return JsonResult.success(sewagePartService.queryPageAllByName(keyWord,areaCode,pageNum,pageSize));
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewagePart:query")
    public JsonResult<SewagePartDto> findById(@RequestParam @ApiParam(value = "id序号",required = true) @Min(value = 0,message = "序号id错误") Integer id){
        SewagePartDto byId = sewagePartService.findById(id);
        return JsonResult.success(byId);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePart:delete")
    public JsonResult deleteIdIn(@RequestBody List<Integer> stringList){
        int count = sewagePartService.deleteByIdIn(stringList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "新增污水管段")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePart:create")
    public JsonResult createSewageOutlet(@RequestBody @Validated(InsertAttr.class) SewagePartDto dto){
        sewagePartService.create(dto);
        return JsonResult.success();
    }


    @ApiOperation(value = "修改编辑")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePart:update")
    public JsonResult updateSewageOutlet(@Validated(UpdateAttr.class) @RequestBody SewagePartDto dto){
        sewagePartService.update(dto);
        return JsonResult.success();
    }



    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:sewagePart:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            sewagePartService.importExcel(file);
            return JsonResult.success("导入数据成功");
        }catch (IOException e){
            log.error("污水管段数据导入失败原因{}",e.getMessage());
            return JsonResult.error("污水管段数据文件流处理失败,原因: "+e.getMessage());
        }
    }

    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/yearState")
    @TransmissionRequired("guanwang:sewagePart:yearState")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(sewagePartService.getSetupTimeList());
    }


    @ApiOperation(value = "统计 内外径半径")
    @GetMapping(value = "/radiisState")
    @TransmissionRequired("guanwang:sewagePart:radiisState")
    public JsonResult<List<DataDto>> getPartRadiis(){
        return JsonResult.success(sewagePartService.findAll());
    }




    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:sewagePart:export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            sewagePartService.exportExcel(response, "污水管段数据", "污水管段数据", SewagePartDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("污水管段数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }




    @ApiOperation(value = "模板下载")
    @GetMapping("/temeplate")
    @TransmissionRequired("guanwang:sewagePart:temeplate")
    public void downloadExcel (HttpServletResponse response) {
        try {
            sewagePartService.exportTemeplate(response, "污水管段数据模板", "污水管段数据模板", SewagePartDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("污水管段模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("污水管段模板下载失败"),response);
        }

    }

    @ApiOperation(value = "统计 区域数量")
    @GetMapping(value = "/statistics/area")
    @TransmissionRequired("guanwang:sewagePart:statistics:area")
    public JsonResult<List<StatisticsVarCherDto>> getGroupByState(){
        return JsonResult.success(sewagePartService.getGroupByState());
    }

    @ApiOperation(value = "统计 管网总长，管道埋深")
    @GetMapping(value = "/statistics/length")
    @TransmissionRequired("guanwang:sewagePart:statistics:length")
    public JsonResult<RainwaterStatisticsDto> getStatisticsDto(){
        return JsonResult.success(sewagePartService.getStatisticsDto());
    }

    @ApiOperation(value = "统计 管道级别")
    @GetMapping(value = "/statistics/leave")
    @TransmissionRequired("guanwang:sewagePart:statistics:leave")
    public JsonResult<List<StatisticsVarCherDto>> getLeaveList(){
        return JsonResult.success(sewagePartService.getLeaveList());
    }

}
