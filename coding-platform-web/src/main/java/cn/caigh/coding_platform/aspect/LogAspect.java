package cn.caigh.coding_platform.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
  /**
   * 前置增强：目标方法执行之前
   */
  @Before("execution(* cn.caigh.coding_platform.controller.*.*(..))")
  public void beforeMethod(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    System.out.println("【前置增强】 ：" + methodName);
  }


  /**
   * 后置增强：目标方法执行之后执行以下方法体的内容，不管是否发送异常
   */
  @After("execution(* cn.caigh.coding_platform.controller.*.*(..))")
  public void afterMethod(JoinPoint joinPoint) {
    String methodName = joinPoint.getSignature().getName();
    System.out.println("【后置增强】 ：" + methodName);
  }

  /**
   * 返回增强：目标方法正常完毕时执行
   */
  @AfterReturning(value = "execution(* cn.caigh.coding_platform.controller.*.*(..))", returning = "result")
  public void afterReturningAdvice(JoinPoint joinPoint, Object result) {
    String methodName = joinPoint.getSignature().getName();
    System.out.println("【返回增强】 ：" + methodName + "--->" + "result:" + result);
  }
}
