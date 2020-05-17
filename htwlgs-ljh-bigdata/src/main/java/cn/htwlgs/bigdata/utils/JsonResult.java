package cn.htwlgs.bigdata.utils;


public class JsonResult<T> {
    private int code;
    private String msg;
    private static final String MSG_OK = "OK";
    private static final int CODE_OK = 200;
    private static final int CODE_ERROR = 500;

    /**
     * 如果为null，则忽略不输出
     */
//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private JsonResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private JsonResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static JsonResult success() {
        return new JsonResult(CODE_OK, MSG_OK, null);
    }

    public static JsonResult success(String msg) {
        return new JsonResult(CODE_OK, msg, null);
    }

    public static <T> JsonResult<T> success(T data) {
        return new JsonResult(CODE_OK, MSG_OK, data);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(CODE_ERROR, msg, null);
    }


    public static <T> JsonResult<T> success(String msg, T data) {
        return new JsonResult<>(CODE_OK, msg, data);
    }


    public JsonResult() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getMsgOk() {
        return MSG_OK;
    }

    public static int getCodeOk() {
        return CODE_OK;
    }

    public static int getCodeError() {
        return CODE_ERROR;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
