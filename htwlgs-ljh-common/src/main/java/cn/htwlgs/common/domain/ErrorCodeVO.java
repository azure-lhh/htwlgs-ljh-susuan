package cn.htwlgs.common.domain;

/**
 * @description 状态码
 * @auther: lhh
 * @date: 2020/5/15 03:02
 */
public enum ErrorCodeVO {
  OK(200, "成功"),
  FAIL(205, "错误"),
  BAD_REQUEST(400, "糟糕的请求"),
  UNAUTHORIZED(401, "未身份验证"),
  FORBIDDEN(403, "禁止请求中指定的方法"),
  NOT_FOUND(404, "未找到资源"),
  METHOD_NOT_ALLOWED(405, "方法不被允许"),
  REQUEST_TIMEOUT(408, "服务器等候请求时发生超时"),
  UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
  LOCKED(423, "锁定"),
  SERVER_ERROR(500, "服务器内部错误"),
  BAD_GATEWAY(502, "错误网关"),
  SERVICE_UNAVAILABLE(503, "服务不可用"),
  GATEWAY_TIMEOUT(504, "网关超时");

  private Integer code;

  private String message;

  ErrorCodeVO() { }

  ErrorCodeVO(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}