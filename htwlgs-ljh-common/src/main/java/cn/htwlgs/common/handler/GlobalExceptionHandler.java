package cn.htwlgs.common.handler;


import cn.htwlgs.common.domain.ErrorCodeVO;
import cn.htwlgs.common.domain.JsonResult;
import cn.htwlgs.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
import javax.validation.ValidationException;
import java.util.List;
import java.util.Set;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 全局异常处理类
 * @Author lihouhai
 * @Date 2020/4/20 11:50
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("unchecked")
    @ExceptionHandler(BusinessException.class)
    public JsonResult businessException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        return JsonResult.error(ex.getMsg());
    }

    @SuppressWarnings("unchecked")
    @ExceptionHandler(Exception.class)
    public JsonResult exception(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("请求：{}发生错误", request.getRequestURL());
        log.error("具体错误:", ex);
        return JsonResult.error("系统异常：" + ex.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @SuppressWarnings("unchecked")
    @ExceptionHandler({ConstraintViolationException.class, HttpMessageConversionException.class, MethodArgumentNotValidException.class, ServletRequestBindingException.class, BindException.class})
    public JsonResult handleValidationException(Exception e) {
        String msg = "";
        if (e instanceof MethodArgumentNotValidException) {
            //处理请求参数格式错误 字段校验异常
            MethodArgumentNotValidException t = (MethodArgumentNotValidException) e;
            StringBuilder errorInfo = new StringBuilder();
            BindingResult bindingResult = t.getBindingResult();
            for(int i = 0; i < bindingResult.getFieldErrors().size(); i++){
                if(i > 0){
                    errorInfo.append(",");
                }
                FieldError fieldError = bindingResult.getFieldErrors().get(i);
                errorInfo.append(fieldError.getField()).append(" :").append(fieldError.getDefaultMessage());
            }
            msg = errorInfo.toString();
        } else if (e instanceof BindException) {
            BindException t = (BindException) e;
            //参数绑定异常
            List<ObjectError> errors = t.getAllErrors();
            ObjectError error = errors.get(0);
             msg = error.getDefaultMessage();
        }else if (e instanceof HttpMessageConversionException) {
            //参数类型转换错误
            HttpMessageConversionException t = (HttpMessageConversionException) e;
            msg =String.format(t.getCause().getLocalizedMessage(),"类型转换错误");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            //参数类型转换错误
            HttpRequestMethodNotSupportedException t = (HttpRequestMethodNotSupportedException) e;
            msg = ErrorCodeVO.METHOD_NOT_ALLOWED.getMessage();
        }else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException t = (ConstraintViolationException) e;
            StringBuffer sb = new StringBuffer();
            Set<ConstraintViolation<?>> violations =  t.getConstraintViolations();
            for(ConstraintViolation<?> item:violations){
                sb.append(item.getMessage()+"/");
            }
            msg = sb.toString();
        } else if (e instanceof MissingServletRequestParameterException) {
            //controller参数异常
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


    /**
     * 参数校验异常
     *
     * @param e 异常
     * @return 错误信息
     */
    @SuppressWarnings("unchecked")
    @ExceptionHandler(value = ValidationException.class)
    public JsonResult<ErrorCodeVO> handlerValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return JsonResult.error("参数错误"+e.getMessage());
    }




    





}