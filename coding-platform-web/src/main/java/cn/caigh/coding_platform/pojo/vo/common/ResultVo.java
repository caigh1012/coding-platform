package cn.caigh.coding_platform.pojo.vo.common;

import cn.caigh.coding_platform.constants.RspBusinCode;

/*
 * 返回给前端的数据结构
 */
public class ResultVo<T> {
  /**
   * 状态码
   */
  private String code;

  /**
   * 提示信息
   */
  private String message;

  /**
   * 返回的数据
   */
  private T data;

  public ResultVo(String code, String message, T data) {
    this.data = data;
    this.code = code;
    this.message = message;
  }

  /**
   * 成功返回
   * 返回默认消息
   * data 为 null
   */
  public static <T> ResultVo<T> success() {
    return new ResultVo<T>(RspBusinCode.SUCCESS.getCode(), RspBusinCode.SUCCESS.getMessage(), null);
  }

  /**
   * 成功返回
   * 返回默认消息
   *
   * @return data 获取的数据
   */
  public static <T> ResultVo<T> success(T data) {
    return new ResultVo<T>(RspBusinCode.SUCCESS.getCode(), RspBusinCode.SUCCESS.getMessage(), data);
  }

  /**
   * 成功返回
   *
   * @param data    获取的数据
   * @param message 消息
   */
  public static <T> ResultVo<T> success(T data, String message) {
    return new ResultVo<T>(RspBusinCode.SUCCESS.getCode(), message, data);
  }

  /**
   * 失败返回
   */
  public static <T> ResultVo<T> failed() {
    return new ResultVo<T>(RspBusinCode.FAILED.getCode(), RspBusinCode.FAILED.getMessage(), null);
  }

  /**
   * 失败返回
   *
   * @param message 消息
   */
  public static <T> ResultVo<T> failed(String message) {
    return new ResultVo<T>(RspBusinCode.FAILED.getCode(), message, null);
  }

  /**
   * 失败返回
   *
   * @param data 失败返回封装数据
   */
  public static <T> ResultVo<T> failed(T data) {
    return new ResultVo<T>(RspBusinCode.FAILED.getCode(), RspBusinCode.FAILED.getMessage(), data);
  }

  /**
   * 失败返回
   *
   * @param data    失败返回封装数据
   * @param message 消息
   */
  public static <T> ResultVo<T> failed(T data, String message) {
    return new ResultVo<T>(RspBusinCode.FAILED.getCode(), message, data);
  }

  /**
   * @param data    失败返回封装数据
   * @param message 消息
   * @param code    自定义返回Code
   */
  public static <T> ResultVo<T> failed(T data, String message, String code) {
    return new ResultVo<T>(code, message, data);
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
