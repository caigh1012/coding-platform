package cn.caigh.coding_platform.constants;

/**
 * 返回给前端的业务代码
 */
public enum RspBusinCode {
  SUCCESS("0000", "请求成功"),
  TOKENEXPIRED("9997", ""),
  UNLOGIN("9998", ""),
  FAILED("9999", "请求失败");
  private final String code;
  private final String message;

  RspBusinCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
