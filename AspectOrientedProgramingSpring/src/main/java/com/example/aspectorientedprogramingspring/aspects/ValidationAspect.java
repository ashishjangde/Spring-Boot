package com.example.aspectorientedprogramingspring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class ValidationAspect {

    @Pointcut("execution(* com.example.aspectorientedprogramingspring.services.serviceImplementation.*.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();
        long OrderId = (long) args[0];
        if (OrderId > 0) return proceedingJoinPoint.proceed();
        return  "can not proceed with negative orderId";
    }

}
