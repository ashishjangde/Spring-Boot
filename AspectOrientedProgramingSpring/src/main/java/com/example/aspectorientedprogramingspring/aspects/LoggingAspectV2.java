package com.example.aspectorientedprogramingspring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspectV2 {

    @Pointcut("execution(* com.example.aspectorientedprogramingspring.services.serviceImplementation.*.*(..))")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void beforeServiceMethodCall(JoinPoint joinPoint) {
        log.info("beforeServiceMethodCall {}", joinPoint.getSignature());
    }

    @After("pointcut()")
    public void afterServiceMethodCall(JoinPoint joinPoint) {
        log.info("afterServiceMethodCall {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "pointcut()",returning = "returnValue")
    public void afterReturningServiceMethodCall(JoinPoint joinPoint, Object returnValue) {
        log.info("afterReturningServiceMethodCall {}", joinPoint.getSignature());
        log.info("returnValue: {}", returnValue);
    }

    @AfterThrowing("pointcut()")
    public void afterThrowingServiceMethodCall(JoinPoint joinPoint) {
        log.info("afterThrowingServiceMethodCall {}", joinPoint.getSignature());
    }

    @Around("pointcut()")
    public void getMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long initialTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        Long executionTime = System.currentTimeMillis() - initialTime;
        log.info("executionTime: {}", executionTime);
        log.info("result: {}", result);

    }




}
