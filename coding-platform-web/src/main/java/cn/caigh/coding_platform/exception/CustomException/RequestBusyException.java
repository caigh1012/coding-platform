package cn.caigh.coding_platform.exception.CustomException;

/**
 * 请求过于频繁，请稍后再试
 */
public class RequestBusyException extends RuntimeException {
  public RequestBusyException(String message) {
    super(message);
  }
}
