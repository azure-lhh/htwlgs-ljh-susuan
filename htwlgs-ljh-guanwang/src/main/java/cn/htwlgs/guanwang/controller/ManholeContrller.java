package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.ManholeDto;
import cn.htwlgs.guanwang.dtos.StatisticsVarCherDto;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.ManholeService;
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
import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ManholeContrller
 * @Description
 * @Author lihouhai
 * @Date 2020/4/22 9:53
 * @Version 1.0
 */
@Slf4j
@Api(tags = "检查井")
@RestController
@RequestMapping(value = "/manhole")
public class ManholeContrller {

    @Autowired
    private ManholeService manholeService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:manhole:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(manholeService.findAllId());
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    @TransmissionRequired("guanwang:manhole:pageQuery")
    public JsonResult<PageList<ManholeDto>> queryPageAllByName(@RequestParam(value = "keyWord",required = false) @ApiParam("关键字") String keyWord,
                                                   @RequestParam(value = "page",defaultValue = "1") @ApiParam(value ="页码" ,required = true)  int pageNum,
                                                   @RequestParam(value = "size",defaultValue = "10") @ApiParam(value ="每页展示条数" ,required = true)  Integer pageSize){

        PageList<ManholeDto> pageList = manholeService.queryPageAllByName(keyWord, pageNum, pageSize);
        return JsonResult.success(pageList);
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:manhole:query")
    public JsonResult<ManholeDto> findById(@RequestParam @ApiParam(value = "id序号",required = true) @Min(value = 0,message = "序号id错误") Integer id){
        ManholeDto byId = manholeService.findById(id);
        return JsonResult.success(byId);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:manhole:delete")
    public JsonResult deleteIdIn(@RequestBody  List<Integer> stringList){
        int count = manholeService.deleteByIdIn(stringList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "新增检查井")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:manhole:create")
    public JsonResult createSewageOutlet(@Validated(InsertAttr.class)  @RequestBody ManholeDto dto){
        manholeService.create(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改编辑检查井")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:manhole:update")
    public JsonResult updateSewageOutlet(@Validated(UpdateAttr.class) @RequestBody ManholeDto dto){
         manholeService.update(dto);
        return JsonResult.success();
    }



    @ApiOperation(value = "统计 检查井计数")
    @RequestMapping(value = "/getAreaCode",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:manhole:getAreaCode")
    public JsonResult<List<StatisticsVarCherDto>> getAreaCode(){
        return JsonResult.success(manholeService.groupByState());
    }


    @ApiOperation(value = "统计 使用年限")
    @GetMapping(value = "/yearState")
    @TransmissionRequired("guanwang:manhole:yearState")
    public JsonResult<List<StatisticsVarCherDto>> getSetupTimeList(){
        return JsonResult.success(manholeService.getSetupTimeList());
    }



    @ApiOperation(value = "数据导入检查井")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:manhole:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            manholeService.importExcel(file);
            return JsonResult.success("导入数据成功");
        }catch (Exception e){
            log.error("检查井数据导入失败原因{}",e.getMessage());
            return JsonResult.error("检查井数据文件流 处理失败！"+e.getMessage());
        }
    }





    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:manhole:export")
    public void exportExcel1 (HttpServletResponse response,@RequestBody List<Integer> idList) throws IOException {
        try {
            manholeService.exportExcel(response, "检查井数据导出", "检查井数据表", ManholeDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (IOException e){
            log.error("检查井数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }
    }


    @ApiOperation(value = "模板下载")
    @GetMapping("/temeplate")
    @TransmissionRequired("guanwang:manhole:temeplate")
    public void downloadExcel (HttpServletResponse response) {
        try {
            manholeService.exportTemeplate(response, "检查井数据模板", "检查井数据", ManholeDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("模板下载失败"),response);
        }

    }







}
