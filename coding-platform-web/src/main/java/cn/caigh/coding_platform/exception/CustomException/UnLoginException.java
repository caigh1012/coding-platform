package cn.caigh.coding_platform.exception.CustomException;

/**
 * 用户未登录
 */
public class UnLoginException extends RuntimeException {
  public UnLoginException(String message) {
    super(message);
  }
}
