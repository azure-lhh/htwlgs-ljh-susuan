package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.*;
import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
import cn.htwlgs.guanwang.service.SewagePointService;
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
@Api(tags = "污水管点")
@RestController
@RequestMapping(value = "/sewagePoint")
public class SewagePointController {

    @Autowired
    private SewagePointService sewagePointService;

    @ApiOperation(value = "查询全部id")
    @GetMapping(value = "/allId")
    @TransmissionRequired("guanwang:sewagePoint:allId")
    public JsonResult<List<Integer>> findAllId(){
        return JsonResult.success(sewagePointService.findAllId());
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/pageQuery")
    @TransmissionRequired("guanwang:sewagePoint:pageQuery")
    public JsonResult<PageList<SewagePointListDto>> queryPageAllByName(@RequestParam(value = "keyWord",required = false) @ApiParam("管段名称") String keyWord,
                                                                       @RequestParam(value = "areaCode",required = false) @ApiParam("所属行政区划编码") String areaCode,
                                                                       @RequestParam(value = "page",defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                                       @RequestParam(value = "size",defaultValue = "10") @ApiParam("每页展示条数") Integer pageSize){
        PageList<SewagePointListDto> pageList = sewagePointService.getPageList(keyWord,areaCode, pageNum, pageSize);
        return JsonResult.success(pageList);
    }

    @ApiOperation(value = "通过id查询")
    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:sewagePoint:query")
    public JsonResult<SewagePointListDto> findByCode(@RequestParam @ApiParam(value = "id序号",required = true) @NotBlank String id){
        SewagePointListDto byId = sewagePointService.findById(id);
        return JsonResult.success(byId);
    }

    @ApiOperation(value = "批量删除")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePoint:delete")
    public JsonResult deleteIdIn(@RequestBody List<Integer> stringList){
        int count = sewagePointService.deleteByIdIn(stringList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "新增污水管点")
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePoint:create")
    public JsonResult createSewagePoint(@RequestBody @Validated(InsertAttr.class) SewagePointDto dto){
        sewagePointService.create(dto);
        return JsonResult.success();
    }

    @ApiOperation(value = "修改编辑")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:sewagePoint:update")
    public JsonResult updateSewagePoint(@Validated(UpdateAttr.class) @RequestBody SewagePointDto dto){
        sewagePointService.update(dto);
        return JsonResult.success();
    }



    @ApiOperation(value = "数据导入")
    @PostMapping("/import")
    @TransmissionRequired("guanwang:sewagePoint:import")
    public JsonResult importExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            sewagePointService.importExcel(file);
            return JsonResult.success("导入数据成功");
        }catch (IOException e){
            log.error("污水管点数据导入失败原因{}",e.getMessage());
            return JsonResult.error("污水管点数据文件流处理失败,原因: "+e.getMessage());
        }
    }





    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:sewagePoint:export")
    public void exportExcel (HttpServletResponse response,@RequestBody List<Integer> idList) {
        try {
            sewagePointService.exportExcel(response, "污水管点数据", "Sheet1", SewagePointDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (Exception e){
            log.error("污水管点数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }

    }


    @ApiOperation(value = "模板下载")
    @GetMapping("/temeplate")
    @TransmissionRequired("guanwang:sewagePoint:temeplate")
    public void downloadExcel (HttpServletResponse response) {
        try {
            sewagePointService.exportTemeplate(response, "污水管点数据模板", "污水管点", SewagePointDto.class, ExcelTypeEnum.XLSX);
        }catch (Exception e){
            log.error("污水管点模板下载失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("污水管点模板下载失败"),response);
        }
    }







}
