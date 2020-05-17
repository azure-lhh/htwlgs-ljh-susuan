package cn.htwlgs.bigdata.controller;

import cn.htwlgs.bigdata.dtos.*;
import cn.htwlgs.bigdata.service.DataService;
import cn.htwlgs.bigdata.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName WaterMonitorController
 * @Description
 * @Author lihouhai
 * @Date 2020/5/11 14:34
 * @Version 1.0
 */
@Slf4j
@Api(tags = "检测数据")
@RestController
@RequestMapping(value = "/bigdata")
public class WaterMonitorController {

    @Autowired
    DataService dataService;

    @ApiOperation(value = "断面水质评价")
    @RequestMapping(value ="/sectionWater",method = RequestMethod.POST)//完成
    public JsonResult<ResultDto> findSecTionWaTerEvaluate (@Validated  @RequestBody  DataDto dataDto)  {
        ResultDto MonitorDto = dataService.findSecTionWaTerEvaluate(dataDto);
        return JsonResult.success(MonitorDto);
    }


    @ApiOperation(value = "流域（水系）水质评价")
    @RequestMapping(value ="/riveBasin",method = RequestMethod.POST)
    public JsonResult<SectionResultDto> findRiverBasinWaTerEvaluate (@Validated @RequestBody List<DataDto> list)  {
        SectionResultDto MonitorDto = dataService.findRiverWaTerEvaluate(list);
        return JsonResult.success(MonitorDto);
    }

    @ApiOperation(value = "河流水质评价")
    @RequestMapping(value ="/riverWater",method = RequestMethod.POST)
    public JsonResult<SectionResultDto> findRiverWaTerEvaluate (@Validated @RequestBody List<DataDto> list)  {
        SectionResultDto MonitorDto = dataService.findRiverWaTerEvaluate(list);
        return JsonResult.success(MonitorDto);
    }

    @ApiOperation(value = "湖库水质-单站点评价")
    @RequestMapping(value ="/lakeWater/single",method = RequestMethod.POST)
    public JsonResult<ResultDto> findLakeWaTerSingleSiteEvaluate (@Validated @RequestBody DataDto DataDto)  {
        ResultDto MonitorDto = dataService.findSecTionWaTerEvaluate(DataDto);
        return JsonResult.success(MonitorDto);
    }

    @ApiOperation(value = "湖库水质-多站点评价")
    @RequestMapping(value ="/lakeWater/multi",method = RequestMethod.POST)
    public JsonResult<ResultDto> findLakeWaTerMultiSiteEvaluate (@Validated @RequestBody List<DataDto> list)  {
        ResultDto dto =   dataService.findLakeWaTerMultiSiteEvaluate(list);
        return JsonResult.success(dto);
    }


    @ApiOperation(value = "湖库水质评价—多次监测")
    @RequestMapping(value ="/multiMonitor",method = RequestMethod.POST)
    public JsonResult<ResultDto> findMultiMonitorEvaluate (@Validated @RequestBody List<DataDto> list)  {
        ResultDto dto =  dataService.findMultiMonitorEvaluate(list);
        return JsonResult.success(dto);
    }



}
