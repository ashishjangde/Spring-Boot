package com.example.aspectorientedprogramingspring.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    //@Before("execution(* orderPackage(..))")  // use when we have to perform any action on a specific method
    @Before("execution(* com.example.aspectorientedprogramingspring.services.serviceImplementation.ShipmentServiceImplementation.*(..))")
    public void beforeServiceShipment(JoinPoint joinPoint) {
        log.info("Before Service {}", joinPoint.getSignature().getName());
    }

    @Before("within(com.example.aspectorientedprogramingspring.services.*)")  // if we want to track all method in a package
    public void beforeMethodExecution(JoinPoint joinPoint) {                    //we us withing  //we don't have to use execution
        log.info("Before Method within Impl call {}", joinPoint.getSignature().getName());
    }

    @Before("pointcutMy()")
    public void pointcut(JoinPoint joinPoint) {
        log.info("Pointcut annotation  Before {}", joinPoint.getSignature().getName());
    }
    @After("pointcutMy()")
    public void pointcutAfter(JoinPoint joinPoint) {
        log.info("Pointcut annotation After {}", joinPoint.getSignature().getName());
    }
    @Before("@annotation(com.example.aspectorientedprogramingspring.aspects.CustomException)")
    public void customAnnotationPointcut(JoinPoint joinPoint) {
        log.info("Custom Annotation Pointcut {}", joinPoint.getSignature().getName());
    }
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void pointcutMy() {}
}
