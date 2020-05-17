//package cn.htwlgs.guanwang.controller;
//
//import cn.htwlgs.common.annotation.TransmissionRequired;
//import cn.htwlgs.guanwang.dtos.AttachmentDto;
//import cn.htwlgs.guanwang.dtos.groups.InsertAttr;
//import cn.htwlgs.guanwang.dtos.groups.UpdateAttr;
//import cn.htwlgs.guanwang.dtos.upload.DownloadDto;
//import cn.htwlgs.guanwang.dtos.upload.IndexVo;
//import cn.htwlgs.guanwang.dtos.upload.Item;
//import cn.htwlgs.guanwang.service.AttachmentService;
//import cn.htwlgs.guanwang.utils.DownloadUtils;
//import io.minio.MinioClient;
//import io.minio.errors.*;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.utils.DateUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.xmlpull.v1.XmlPullParserException;
//import cn.htwlgs.common.domain.JsonResult;
//import cn.htwlgs.common.utils.HttpUtilsResult;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.List;
//
//@Api(tags = "附件管理")
//@RestController
//@RequestMapping("/attachment")
//@Slf4j
//public class UploadController {
//
//    @Autowired
//    private MinioClient minioClient;
//
//    @Autowired
//    AttachmentService attachmentService;
//    @Autowired
//    private DownloadUtils downloadUtils;
//
//    @ApiOperation("上传")
//    @PostMapping("/upload")
//    @TransmissionRequired("guanwang:attachment:upload")
//    public JsonResult index( MultipartFile file, @ApiParam(value = "分类", required = true) @RequestParam(value = "type", required = true, defaultValue = "") String type) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidExpiresRangeException {
//        IndexVo vo = new IndexVo();
//        String bucketName = "files";
//        String uplodaTime = DateUtils.formatDate(new Date(), "yyyy/MMdd");
//        String fileName = type + "/" + uplodaTime + "/" + file.getOriginalFilename();
//        Item item = new Item();
//        minioClient.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
////        List<Item> items = vo.getFiles();
//        item.setName(file.getOriginalFilename());
//        item.setFileSuffix(DownloadUtils.getFileType(file));
//        item.setUrl(bucketName + "/" + fileName);
//        item.setUploadTime(LocalDateTime.now());
////        items.add(item);
//        vo.setFiles(item);
//        return JsonResult.success(vo);
//    }
//
//    @ApiOperation("下载")
//    @GetMapping("/download")
//    @TransmissionRequired("guanwang:attachment:download")
//    public void downloadFile(HttpServletResponse response,DownloadDto dto) throws IOException{
//        downloadUtils.toZip(dto,response);
//    }
//
//
//
//    @ApiOperation("编辑文件信息")
//    @PostMapping("/update")
//    @TransmissionRequired("guanwang:attachment:update")
//    public JsonResult update( @RequestBody  @Validated({UpdateAttr.class}) AttachmentDto dto){
//        attachmentService.update(dto);
//        return JsonResult.success();
//    }
//
//
//    @ApiOperation("清空文件信息")
//    @PostMapping("/delete")
//    @TransmissionRequired("guanwang:attachment:delete")
//    public JsonResult deleteByIdIn(@RequestBody List<String> idList) {
//        int delete = attachmentService.deleteByIdIn(idList);
//        return JsonResult.success(delete);
//    }
//
//
//    @ApiOperation("添加文件信息")
//    @PostMapping("/create")
//    @TransmissionRequired("guanwang:attachment:create")
//    public JsonResult create(@Validated(InsertAttr.class) @RequestBody AttachmentDto dto){
//        attachmentService.create(dto);
//        return JsonResult.success();
//    }
//
//    @ApiOperation("查询附件")
//    @GetMapping("/query")
//    @TransmissionRequired("guanwang:attachment:query")
//    public JsonResult findAllByEngTypeAndEngId(@RequestParam("engId") @ApiParam(value = "工程id",required = true) Integer engId,
//                                               @RequestParam("engType") @ApiParam(value = "0子工程或1总工程",required = true) String engType) {
//        List<AttachmentDto>  dtos = attachmentService.findAllByEngTypeAndEngId(engId,Integer.valueOf(engType));
//        return JsonResult.success(dtos);
//    }
//
//
//
//
//
//
//
//}
//
//
