package cn.htwlgs.bigdata.handler;


import cn.htwlgs.bigdata.exception.BusinessException;
import cn.htwlgs.bigdata.utils.HttpUtilsResult;
import cn.htwlgs.bigdata.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.net.BindException;
import java.util.stream.Collectors;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理类
 * @Author lihouhai
 * @Date 2020/4/20 11:50
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(HttpUtilsResult.class);

    @ExceptionHandler(BusinessException.class)
    public JsonResult businessException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        return JsonResult.error(ex.getMsg());
    }

    @ExceptionHandler
    public JsonResult exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("请求：{}发生错误", request.getRequestURL());
        log.error("具体错误:", ex);
        return JsonResult.error("系统异常：" + ex.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class, ServletRequestBindingException.class, BindException.class})
    public JsonResult handleValidationException(Exception e) {
        String msg = "";
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException t = (MethodArgumentNotValidException) e;
            msg = getBindingResultMsg(t.getBindingResult());
        } else if (e instanceof BindException) {
//            BindException t = (BindException) e;
//            msg = getBindingResultMsg(t.getBindingResult());
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) e;
            msg = t.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
        } else if (e instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException t = (MissingServletRequestParameterException) e;
            msg = t.getParameterName() + " 不能为空";
        } else if (e instanceof MissingPathVariableException) {
            MissingPathVariableException t = (MissingPathVariableException) e;
            msg = t.getVariableName() + " 不能为空";
        } else {
            msg = "必填参数缺失";
        }
        log.error("发生异常: {}",msg);
        return JsonResult.error(msg);
    }

    private String getBindingResultMsg(BindingResult result) {
        return result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }


}