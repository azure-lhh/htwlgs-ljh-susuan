package cn.htwlgs.guanwang.utils;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.exception.BusinessException;
import cn.htwlgs.common.utils.HttpUtilsResult;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;


@Slf4j
public class EasyExcelUtil {


    /**
     * 功能描述：根据文件路径来导入Excel
     *
     * @date 2020/4/21 13:07
     * @param filePath 文件全路径
     * @param analysisEventListener 监视器
     * @param pojoClass Excel实体类
     * @return
     */
    public static <T> void importExcel(String filePath, Class<T> pojoClass, AnalysisEventListener analysisEventListener) {
        if (StringUtils.isBlank(filePath)) {
            throw new BusinessException("查找文件失败");
        }
        try {
            EasyExcel.read(filePath, pojoClass, analysisEventListener).sheet().doRead();//第一种类
//            ExcelReader excelReader = EasyExcel.read(filePath, pojoClass, analysisEventListener).build();
//            ReadSheet readSheet = EasyExcel.readSheet(0).build();
//            excelReader.read(readSheet);
//            // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//            excelReader.finish();
        } catch (Exception e) {
            log.error("请传入规范的Excel文件:{}",e.getMessage());
            throw new BusinessException("请传入规范的Excel文件");
        }
    }


    /**
     * 功能描述：Web导入
     *
     * @date 2020/4/21 13:07
     * @param file 上传的文件
     * @param analysisEventListener 监视器
     * @return
     */
    public static <T> void importExcel(MultipartFile file, Class<?> pojoClass,AnalysisEventListener<T> analysisEventListener) {
        if (file == null) {
            log.error("请选择你要导入的Excel文件");
            throw new BusinessException("请选择你要导入的Excel文件");
        }
        try {
            EasyExcel.read(file.getInputStream(), pojoClass, analysisEventListener).sheet().doRead();
        } catch (Exception e) {
            log.error("请传入规范的Excel文件");
            throw new BusinessException("请传入规范的Excel文件");
        }

    }

    /**
     * 功能描述：本地路径导出
     *
     * @param list
     * @param sheetName
     * @param pojoClass
     * @param filePath  全文件名
     */
    public static void exportExcel(List<? extends Object> list, String sheetName, Class<?> pojoClass, String filePath) {
        if (StringUtils.isBlank(filePath)) {
            log.error("查找文件失败");
            throw new BusinessException("查找文件失败");

        }
        try {
            EasyExcel.write(filePath,pojoClass).sheet(sheetName).doWrite(list);
        } catch (Exception e) {
            log.error("数据导出失败原因:{}",e.getMessage());
            throw new BusinessException("数据读取失败");
        }
    }

    /**
     * @Description: 导出

     * @param response
     * @param data      数据
     * @param fileName      用户列表
     * @param sheetName     用户列表
     * @param excelType     ExcelTypeEnum.XLSX
     * @param pojoClass  实体类
     * @return: void
     *
     */
    public static void exportExcel(HttpServletResponse response,List<? extends Object> data, String fileName, String sheetName,Class<?> pojoClass,ExcelTypeEnum excelType) throws IOException {
        //表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

        //设置内容靠左对齐
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        Set<String> excludeColumnFiledNames = new HashSet<String>();
        excludeColumnFiledNames.add("updateUser");
        excludeColumnFiledNames.add("updateTime");
        excludeColumnFiledNames.add("createTime");
        excludeColumnFiledNames.add("createUser");
        excludeColumnFiledNames.add("id");
        EasyExcel.write(getOutputStream(fileName,excelType, response), pojoClass).excelType(excelType).excludeColumnFiledNames(excludeColumnFiledNames).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
    }




    private static OutputStream getOutputStream(String fileName,ExcelTypeEnum excelType, HttpServletResponse response) throws IOException {
//
            try {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setCharacterEncoding("utf8");
                String centent = String.format("attachment;filename=%s%s",fileName,excelType.getValue());
                response.setHeader("Content-Disposition", centent);
            } catch (Exception e) {
                HttpUtilsResult.writeError(JsonResult.error("文件流处理失败"),response);
            }
        return response.getOutputStream();
    }
}









