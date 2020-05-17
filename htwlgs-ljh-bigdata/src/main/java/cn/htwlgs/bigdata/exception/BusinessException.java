package cn.htwlgs.bigdata.exception;


/**
 * @ClassName BusinessRuntimeException
 * @Description
 * @Author lihouhai
 * @Date 2020/4/20 11:35
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {
    private int code = 0;
    private String msg;

    public BusinessException() {
    }

    public BusinessException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String toString() {
        return this.msg;
    }
}
