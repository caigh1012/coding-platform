package cn.caigh.coding_platform.constants;

/**
 * 全局异常捕获提示类型消息
 */
public enum GlobalExceptionMsg {
  UnLoginMsg("用户未登录，请重新登录！"),
  BadCredentialMsg("用户名错误或密码错误，请重新尝试"),
  JWTVerificationMsg("非法Token"),
  TokenExpiredMsg("token已过期，请重新登录！"),
  ExceptionMsg("系统服务器异常，请稍后重试"),
  AuthenticationMsg("用户未登录或登录失败，请重新登录"),
  MethodArgumentNotValidMsg("请求参数错误"),
  AccessDeniedExceptionMsg("权限不足，请联系管理员"),
  RequestBusyExceptionMsg("请求过于频繁，请稍后再试");

  private final String message;

  GlobalExceptionMsg(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
