package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.SewageOutletService;
import cn.htwlgs.guanwang.utils.*;
import cn.htwlgs.guanwang.dtos.SewageOutletDto;
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
 * @ClassName TestController
 * @Description
 * @Author lihouhai
 * @Date 2020/4/20 11:50
 * @Version 1.0
 */
@Slf4j
@Api(tags = "排污口")
@RestController
@RequestMapping(value = "/sewageOutlet")
public class SewageOutletController {
    @Autowired
    private SewageOutletService sewageOutletService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:sewageOutlet:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(sewageOutletService.findAllId());
    }


    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    @TransmissionRequired("guanwang:sewageOutlet:pageQuery")
    public JsonResult<PageList<SewageOutletDto>> queryPageAllByName(@RequestParam(value = "keyWord",required = false) @ApiParam("关键字") String keyWord,
                                                   @RequestParam(value = "page",defaultValue = "1") @ApiParam(value ="页码" ,required = true)  int pageNum,
                                                   @RequestParam(value = "size",defaultValue = "10") @ApiParam(value ="每页展示条数" ,required = true)  Integer pageSize){

        PageList<SewageOutletDto> pageList = sewageOutletService.queryPageAllByName(keyWord, pageNum, pageSize);
        return JsonResult.success(pageList);
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewageOutlet:query")
    public JsonResult<SewageOutletDto> findById(@RequestParam @ApiParam(value = "id序号",required = true) @Min(value = 0,message = "序号id错误") Integer id){
        SewageOutletDto byId = sewageOutletService.findById(id);
            return JsonResult.success(byId);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewageOutlet:delete")
    public JsonResult deleteIdIn(@RequestBody List<Integer> stringList){
        int count = sewageOutletService.deleteByIdIn(stringList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "新增排污口")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewageOutlet:create")
    public JsonResult createSewageOutlet(@RequestBody @Validated(InsertAttr.class) SewageOutletDto dto){
            sewageOutletService.create(dto);
            return JsonResult.success();
    }

    @ApiOperation(value = "修改编辑")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewageOutlet:update")
    public JsonResult updateSewageOutlet(@Validated(UpdateAttr.class) @RequestBody SewageOutletDto dto){
            sewageOutletService.update(dto);
            return JsonResult.success();
    }
    @ApiOperation(value = "排污口计数统计")
    @RequestMapping(value = "/getAreaCode",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewageOutlet:getAreaCode")
    public JsonResult getAreaCode(){
        return JsonResult.success(sewageOutletService.groupByState());
    }

    @ApiOperation(value = "统计 排放类型计数统计")
    @RequestMapping(value = "/groupByType",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewageOutlet:groupByType")
    public JsonResult getGroupByType(){
        return JsonResult.success(sewageOutletService.getGroupByType());
    }

    @ApiOperation(value = "统计 排放方式计数统计")
    @RequestMapping(value = "/groupByPFWay",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewageOutlet:groupByPFWay")
    public JsonResult getGroupByPFWay(){
        return JsonResult.success(sewageOutletService.getGroupByPFWay());
    }

    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/yearState")
    @TransmissionRequired("guanwang:sewageOutlet:yearState")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(sewageOutletService.getSetupTimeList());
    }





    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    public JsonResult importExcel(@RequestParam("file")MultipartFile file) throws IOException {
        try {
            sewageOutletService.importExcel(file);
            return JsonResult.success("导入数据成功");
        }catch (Exception e){
            log.error("排污口数据导入失败原因{}",e.getMessage());
            return JsonResult.error("排污口数据文件流 处理失败！"+e.getMessage());
        }
    }



    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            sewageOutletService.exportExcel(response, "排污口数据", "排污口数据", SewageOutletDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("检查井数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }


    @ApiOperation(value = "模板下载")
    @GetMapping("/temeplate")
    public void downloadExcel (HttpServletResponse response) {
        try {
            sewageOutletService.exportTemeplate(response, "排污口数据模板", "排污口数据", SewageOutletDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("排污口模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("排污口模板下载失败"),response);
        }
    }




}
