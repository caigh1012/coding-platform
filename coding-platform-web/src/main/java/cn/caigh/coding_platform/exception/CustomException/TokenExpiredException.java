package cn.caigh.coding_platform.exception.CustomException;

/**
 * token已过期，请重新登录
 */
public class TokenExpiredException extends RuntimeException {
  public TokenExpiredException(String message) {
    super(message);
  }
}
