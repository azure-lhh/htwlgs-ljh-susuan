package cn.htwlgs.guanwang.controller;

import cn.htwlgs.common.annotation.TransmissionRequired;
import cn.htwlgs.guanwang.dtos.EngInfoDto;
import cn.htwlgs.guanwang.dtos.EngInfoHzDto;
import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
import cn.htwlgs.guanwang.service.EngInfoService;
import cn.htwlgs.guanwang.utils.PageList;
import com.alibaba.excel.support.ExcelTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.utils.HttpUtilsResult;
@Slf4j
@Api(tags = "总工程信息管理")
@RestController
@RequestMapping(value = "/pro")
public class EngInfoController {

    @Autowired
    EngInfoService engInfoService;

    @ApiOperation(value = "获取总工程管理信息列表")
    @RequestMapping(value = "/pageQuery",method = RequestMethod.GET)
    @TransmissionRequired("guanwang:pro:pageQuery")
    public JsonResult<PageList<EngInfoDto>> getEngList(@RequestParam(value = "keyWord",required = false) @ApiParam("管段编号匹配") String keyWord,
                                                       @RequestParam(value = "engType") @ApiParam(value ="检查0或缺陷1" ,required = true) int engType,
                                                       @RequestParam(value = "areaCode",required = false) @ApiParam("行政区域Code") String areaCode,
                                                       @RequestParam(value = "page",defaultValue = "1") @ApiParam(value ="页码" ,required = true)  int pageNum,
                                                       @RequestParam(value = "size",defaultValue = "10") @ApiParam(value ="每页展示条数" ,required = true)  Integer pageSize){
        PageList<EngInfoDto> pageList = engInfoService.getEngList(engType,keyWord, areaCode,pageNum, pageSize);
        return JsonResult.success(pageList);
    }

    @ApiOperation(value = "新增总工程")
    @RequestMapping(value ="/insertEng",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:pro:insertEng")
    public JsonResult insertEng ( @RequestBody @Validated EngInfoHzDto dto)  {
        engInfoService.insertEng(dto);
        return JsonResult.success();
    }


    @ApiOperation(value = "删除总工程信息")
    @RequestMapping(value = "/deleteEng",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:pro:deleteEng")
    public JsonResult deleteEng(@RequestBody List<String> idList){
        int count = engInfoService.deleteEng(idList);
        return JsonResult.success(count);
    }

    @ApiOperation(value = "修改总工程信息")
    @RequestMapping(value = "/updateEng",method = RequestMethod.POST)
    @TransmissionRequired("guanwang:pro:updateEng")
    public JsonResult updateHzEngDetect(@Validated(UpdateAttr.class) @RequestBody EngInfoHzDto dto){
         engInfoService.updateEngInfoHz(dto);
        return JsonResult.success();
    }





    @ApiOperation(value = "数据导出")
    @PostMapping("/export")
    @TransmissionRequired("guanwang:pro:export")
    public void exportExcel1 (HttpServletResponse response,@RequestBody List<Integer> idList) throws IOException {
        try {
            engInfoService.exportExcel(response, "总工程数据导出", "总工程数据表", EngInfoHzDto.class, ExcelTypeEnum.XLSX,idList);
        }catch (IOException e){
            log.error("总工程数据导出失败原因{}",e.getMessage());
            HttpUtilsResult.writeError(JsonResult.error("下载文件失败"),response);
        }
    }





}
