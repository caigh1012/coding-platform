package cn.caigh.coding_platform.exception;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * SpringSecurity 授权异常
 * 注意：
 * 此处实现了把 Handler 委托给该 Resolver（解析器），这样可以使用 Exception Handler 方法通过 ControllerAdvice 来处理此 Security 异常
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  @Resource
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
    resolver.resolveException(request, response, null, accessDeniedException);
  }
}
