package cn.caigh.coding_platform.exception;

import cn.caigh.coding_platform.constants.GlobalExceptionMsg;
import cn.caigh.coding_platform.exception.CustomException.TokenExpiredException;
import cn.caigh.coding_platform.exception.CustomException.UnLoginException;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 用户未登录
   */
  @ExceptionHandler({UnLoginException.class})
  public ResultVo<String> UnLoginExceptionHandler(UnLoginException e) {
    return ResultVo.failed(e.getMessage());
  }

  /**
   * 认证异常
   * 注意：
   * 当用户未注册时或者密码错误时，统一提示：用户名错误或密码错误，请重新尝试
   * 在Spring Security的默认行为中，为了不暴露用户是否存在的信息（防止用户名枚举攻击）
   */
  @ExceptionHandler({BadCredentialsException.class, AuthenticationException.class})
  public ResultVo<String> BadCredentialsExceptionHandler(BadCredentialsException e) {
    return ResultVo.failed(GlobalExceptionMsg.BadCredentialMsg.getMessage());
  }

  /**
   * 校验 JWT 时异常
   * 注意：
   * 一般防止token被篡改或非本系统发放的token时报出 “非法token”
   */
  @ExceptionHandler({JWTVerificationException.class})
  public ResultVo<String> JWTVerificationExceptionHandler(JWTVerificationException e) {
    return ResultVo.failed(GlobalExceptionMsg.JWTVerificationMsg.getMessage());
  }

  /**
   * 用户会话已过期
   */
  @ExceptionHandler({TokenExpiredException.class})
  public ResultVo<String> TokenExpiredExceptionHandler(TokenExpiredException e) {
    return ResultVo.failed(e.getMessage());
  }

  /**
   * 拒绝访问异常处理程序
   * 注意：无权限异常处理
   */
  @ExceptionHandler({AccessDeniedException.class})
  public ResultVo<String> accessDeniedExceptionHandler(AccessDeniedException e) {
    return ResultVo.failed(GlobalExceptionMsg.AccessDeniedExceptionMsg.getMessage());
  }

  /**
   * 对于 Dto 类的参数校验异常统一抛出
   * 注意：
   * 暂不做提示具体某个参数错误，统一回复：参数错误
   */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResultVo<String> methodArgumentNotValidHandler(MethodArgumentNotValidException e) {
    return ResultVo.failed(GlobalExceptionMsg.MethodArgumentNotValidMsg.getMessage());
  }

  /**
   * 运行时异常处理器
   */
  @ExceptionHandler({RuntimeException.class})
  public ResultVo<String> runtimeExceptionHandler(RuntimeException e) {
    log.error("系统服务器异常：{}", e.getMessage());
    return ResultVo.failed(GlobalExceptionMsg.ExceptionMsg.getMessage());
  }

  /**
   * 异常处理器
   * 注意：用于最后兜底处理
   */
  @ExceptionHandler({Exception.class})
  public ResultVo<String> exceptionHandler(Exception e) {
    return ResultVo.failed(GlobalExceptionMsg.ExceptionMsg.getMessage());
  }
}
