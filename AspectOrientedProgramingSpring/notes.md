# Aspect-Oriented Programming (AOP)

## Introduction to Aspect-Oriented Programming (AOP) & Cross-Cutting Concerns
- **Cross-Cutting Concerns**: Aspects of a program that affect multiple modules and cannot be modularized easily using traditional object-oriented (OO) design.
- **AOP**: A programming paradigm that helps to modularize cross-cutting concerns and weave them into the main business logic, keeping the core code clean and separate from additional concerns.

In the **Spring** framework, AOP provides a seamless solution for managing cross-cutting concerns like logging, security, and transaction management.

## AOP Overview
- **OOP vs AOP**: Object-Oriented Programming (OOP) is widely used to promote code reusability, but it often falls short in addressing cross-cutting concerns. AOP complements OOP by allowing concerns that span multiple parts of an application to be modularized.

- **Goal of AOP**: To separate concerns in a software application and modularize them independently of the main business logic.

## Benefits of AOP
- **Cleaner and more focused code**
- **Reduced code duplication**
- **Enhanced modularity**
- **Loose coupling and enhanced flexibility**

## Key Concepts in AOP
- **Aspect**: A module that encapsulates a single cross-cutting concern.
- **Join Point**: Specific points in the program where additional behavior or logic can be injected.
- **Advice**: The code associated with an aspect, executed when a particular join point is reached.
- **Pointcut**: Expressions that match certain join points to determine where advice should be applied.
- **Weaving**: The process of integrating aspects into the main business logic. Weaving can occur at compile-time, load-time, or runtime.


# Declaring Pointcuts in Spring AOP

## Introduction to Pointcuts
In **Spring AOP (Aspect-Oriented Programming)**, pointcut expressions define **join points** where **advice** (cross-cutting concerns such as logging, security, transactions, etc.) should be applied. These expressions use **AspectJ syntax** and can be declared either using annotations or through XML configurations in Spring applications.

---

## Common Pointcut Types

### 1. **Execution Pointcut**
- **Description**: The most widely used pointcut expression, it targets method executions within classes.
- **Use Case**: When you need to apply advice to specific methods in your classes. This is useful for monitoring method calls, adding logging, security, or transactional behavior.

### 2. **Within Pointcut**
- **Description**: This pointcut is used when you want to apply advice to all methods in a particular class or package without focusing on specific methods.
- **Use Case**: Useful when you want to apply advice to everything within a class or package, such as adding logging to all service methods within a service package.

### 3. **@Annotation Pointcut**
- **Description**: The `@annotation` pointcut targets methods that are annotated with a specific annotation. This can include both built-in annotations like `@Transactional` or custom annotations.
- **Use Case**: Apply advice to any method that is annotated with a certain annotation, useful for cross-cutting concerns like transaction management or handling custom annotations like `@CustomException`.

---

## Example Code: Declaring Pointcuts

```java
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

    // Advice applied before any method in ShipmentServiceImplementation class
    @Before("execution(* com.example.aspectorientedprogramingspring.services.serviceImplementation.ShipmentServiceImplementation.*(..))")
    public void beforeServiceShipment(JoinPoint joinPoint) {
        log.info("Before Service {}", joinPoint.getSignature().getName());
    }

    // Advice applied to all methods within a package
    @Before("within(com.example.aspectorientedprogramingspring.services.*)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        log.info("Before Method within Impl call {}", joinPoint.getSignature().getName());
    }

    // Custom pointcut for specific annotation and method
    @Before("pointcutMy()")
    public void pointcut(JoinPoint joinPoint) {
        log.info("Pointcut annotation Before {}", joinPoint.getSignature().getName());
    }

    @After("pointcutMy()")
    public void pointcutAfter(JoinPoint joinPoint) {
        log.info("Pointcut annotation After {}", joinPoint.getSignature().getName());
    }

    // Advice applied to methods annotated with @CustomException
    @Before("@annotation(com.example.aspectorientedprogramingspring.aspects.CustomException)")
    public void customAnnotationPointcut(JoinPoint joinPoint) {
        log.info("Custom Annotation Pointcut {}", joinPoint.getSignature().getName());
    }

    // Pointcut for @Transactional annotation
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void pointcutMy() {}
}

```

# Spring AOP Advice: The Basics

Ever wondered how to add extra behavior to your methods without changing their code? That's where Spring AOP Advice comes in handy!

## What is Advice?

Think of Advice as a way to say "Hey, do this extra stuff before/after/around my method runs." It's like having a helpful assistant for your methods.

## Types of Advice

1. **@Before**: The eager beaver
   - Runs before your method
   - Great for setup or checks

2. **@After**: The cleanup crew
   - Runs after your method, no matter what happens
   - Perfect for tidying up

3. **@AfterReturning**: The successful finisher
   - Only runs if your method doesn't throw a fit (er, exception)
   - Useful when you need to know the result

4. **@AfterThrowing**: The problem handler
   - Jumps in when things go wrong
   - Your method's personal firefighter

5. **@Around**: The flexible friend
   - Can do stuff before and after your method
   - The Swiss Army knife of advice - super versatile!

## Pro Tips

- Start simple with @Before
- Use the JoinPoint to peek at method details
- Check out the official docs for more cool tricks!

Remember, with great power comes great responsibility. Use advice wisely to keep your code clean and your methods happy!

# Spring AOP Advice Notes

## Declaring Advice

Advice is associated with a pointcut expression and runs before, after, or around method executions matched by the pointcut. The pointcut expression may be either an inline pointcut or a reference to a named pointcut.

## Advice Types

### 1. @Before Advice
- Runs before the method execution
- Can capture the JoinPoint, which offers useful information like:
    - Method name
    - Method arguments
    - Other details

### 2. @After Advice
- Runs after the method finishes execution
- Executes regardless of whether the method returns normally or throws an exception

### 3. @AfterReturning Advice
- Similar to @After, but runs only after normal execution of the method
- Can be used to access the returning object

### 4. @AfterThrowing Advice
- Similar to @After, but runs only after an exception is thrown during method execution

### 5. @Around Advice
- Allows actions to be taken either before or after a JoinPoint method is run
- Can be used to:
    - Return a custom value
    - Throw an exception
    - Let the method run and return normally

## Additional Notes
- So far, we have been using the @Before Advice annotation simply
- Spring AOP provides more advanced functionalities
- Official documentation can be found [here](https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/advice.html)


