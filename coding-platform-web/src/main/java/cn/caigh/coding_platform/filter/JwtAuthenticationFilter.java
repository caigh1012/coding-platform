package cn.caigh.coding_platform.filter;

import cn.caigh.coding_platform.constants.GlobalExceptionMsg;
import cn.caigh.coding_platform.constants.RedisKey;
import cn.caigh.coding_platform.exception.CustomException.TokenExpiredException;
import cn.caigh.coding_platform.exception.CustomException.UnLoginException;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.utils.JwtUtil;
import cn.caigh.coding_platform.utils.RedisUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Objects;

/**
 * Jwt 验证过滤器
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Resource
  private RedisUtil redisUtil;

  /**
   * 通过该类的resolveException方法抛出自定义异常交给全局异常处理器处理
   */
  @Resource
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // 1. 从请求头中的 Authorization 获取 token
    String token = request.getHeader("Authorization");

    if (StrUtil.isEmpty(token)) {
      // 没有 token 直接放行，后续过滤器中该请求会被拦截
      filterChain.doFilter(request, response);
      return;
    }

    // 2. 校验token
    try {
      JwtUtil.verifyToken(token);
    } catch (JWTVerificationException e) {
      resolver.resolveException(request, response, null, e); // 校验异常则抛出在全局异常处理器拦截处理
      return;
    }

    // 3. 解析token
    DecodedJWT jwt = JwtUtil.parseToken(token);
    String username = jwt.getIssuer();
    String uuid = jwt.getClaim("uuid").asString();

    String tokenKey = RedisKey.getTokenKey(username);

    String redisuuid = (String) redisUtil.get(tokenKey);

    // 如果不存在key，则用户表示未登录，例如：appId:token:phone
    if (!redisUtil.hasKey(tokenKey)) {
      resolver.resolveException(request, response, null, new UnLoginException(GlobalExceptionMsg.UnLoginMsg.getMessage()));
      return;
    }

    // 比对 redis 存储的 uuid 和 token 解析出来的 uuid
    // 如果不一致代表用户登录覆盖过 uuid ，统一提示：token已过期，请重新登录
    if (!Objects.equals(uuid, redisuuid)) {
      resolver.resolveException(request, response, null, new TokenExpiredException(GlobalExceptionMsg.TokenExpiredMsg.getMessage()));
      return;
    }

    // 如果存储用户信息也是过期（用户信息过期时间是设置的30分钟），统一提示：token已过期，请重新登录
    String userKey = RedisKey.getUserKey(uuid);
    Long l = redisUtil.getExpire(userKey);
    if (l == -2) {
      redisUtil.delete(tokenKey);
      resolver.resolveException(request, response, null, new TokenExpiredException(GlobalExceptionMsg.TokenExpiredMsg.getMessage()));
      return;
    }

    // 重置存储用户的过期时间，不断续期
    redisUtil.expire(userKey, 1800);

    try {
      User user = (User) redisUtil.get(userKey);
      // 将用户信息（保护权限信息）存入SecurityContextHolder，完成验证
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (Exception e) {
      resolver.resolveException(request, response, null, e);
      return;
    }
    filterChain.doFilter(request, response);
  }
}
