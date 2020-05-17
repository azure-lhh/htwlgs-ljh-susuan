package cn.htwlgs.common.utils;


import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: lihouhai
 * Date: 2019/11/5
 * Time: 6:09
 * Description: No Description
 */

public class HttpUtilsResult {

    private static final Logger log = LoggerFactory.getLogger(HttpUtilsResult.class);
    /**
     * 异常输出工具类
     */
    public static void writeError(JsonResult bs, HttpServletResponse response)  {
        try {
            response.reset();
            response.setContentType("application/json,charset=utf-8");
            response.setStatus(bs.getCode());
            response.setCharacterEncoding("UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), bs);
        } catch (IOException e) {
            log.error("数据请求处理异常原因：{}",e.getMessage());
            throw  new BusinessException("数据请求处理异常");
        }
    }
    /**
     * SUCESS输出工具类
     */
    public static void writeSuccess(JsonResult bs, HttpServletResponse response) {
        try {
            response.setContentType("application/json,charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(bs.getCode());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), bs);
        } catch (IOException e) {
            log.error("数据请求处理异常原因：{}",e.getMessage());
            throw  new BusinessException("数据请求处理异常");
        }
    }



}