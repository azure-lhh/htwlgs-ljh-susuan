package cn.htwlgs.guanwang.utils;

import cn.htwlgs.guanwang.dtos.Constants;
import cn.htwlgs.guanwang.dtos.upload.DownloadDto;
import cn.htwlgs.common.exception.BusinessException;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@Component
public class DownloadUtils {

    private static final int BUFFER_SIZE = 2 * 1024;
//    @Autowired
//    private  MinioClient minioClient;
//
//    /**
//     * 压缩成ZIP 方法
//     * @param dto 压缩文件dto
//     * @param response
//     * @throws RuntimeException 压缩失败会抛出运行时异常
//     */
//    public  void toZip(DownloadDto dto, HttpServletResponse response) throws IOException{
//        String zipFileName = dto.getFileName()+".zip";
//        String fileName = URLEncoder.encode(zipFileName, "UTF-8");
//        response.setContentType("application/octet-stream"); // 不同类型的文件对应不同的MIME类型 // 重点突出
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//
//        ZipOutputStream zos = null;
//        String bucketName = Constants.FILE_KEY;
//        try {
//            zos = new ZipOutputStream(response.getOutputStream());
//            List<String> fileUrls = Arrays.asList(dto.getFileUrls().split(","));
//            for(String item : fileUrls) {
//                String name = item.substring(item.indexOf("/") + 1);
////                ObjectStat stat = minioClient.statObject(bucketName, name);
////                if(stat == null){
////                    throw new BusinessException("文件不存在或已被删除");
////                }
//                byte[] buf = new byte[BUFFER_SIZE];
//                zos.putNextEntry(new ZipEntry(name));
//                int len;
//                InputStream in = minioClient.getObject(bucketName, name);
//                BufferedInputStream bis = new BufferedInputStream(in);
//                while ((len = bis.read(buf)) != -1) {
//                    zos.write(buf, 0, len);
//                }
//                zos.closeEntry();
//                bis.close();
//            }
//        } catch (Exception e) {
//            throw new BusinessException("压缩失败："+e.getMessage());
//        } finally {
//            if (zos != null) {
//                try {
//                    zos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }


    /**
     * 获取文件类型
     *
     * @return s
     */
    public static String getFileType(MultipartFile file) {
        String[] s = file.getOriginalFilename().split("\\.");
        List list = new ArrayList();
        for (String s1 : s) {
            list.add(s1);
        }
        if (list.size() > 1) {
            return list.get(list.size() - 1).toString();
        }
        return null;
    }


    /**
     * 获取文件名称
     *
     * @param file s
     * @return s
     */
    public static String getFileName(MultipartFile file) {
        String filename = file.getOriginalFilename().replace("." + getFileType(file), "");
        return filename;
    }


}
