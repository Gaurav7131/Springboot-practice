package com.example.demo.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformaceAspect {

    // Pointcut:predicate exp matches joinPoint.(method execution for spring aop)
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceLayerPointcut() {
    }

    // Before:Executes before method entry
    @Before("serviceLayerPointcut()")
    public void logEntry(JoinPoint joinPoint) {
        System.out.println("Before:method entering:" + joinPoint.getSignature().getName());
    }

    // AfterReturning
    @AfterReturning(pointcut = "serviceLayerPointcut()", returning = "result")
    public void logSuccess(JoinPoint joinPoint, Object result) {
        System.out.println("After returning:" + joinPoint.getSignature().getName() + "returned" + result);
    }

    // AfterThrowing
    @AfterThrowing(pointcut = "serviceLayerPointcut()", throwing = "ex")
    public void LogError(JoinPoint joinPoint, Throwable ex) {
        System.out.println("After throwing exception:" + joinPoint.getSignature().getName() + ":" + ex.getMessage());
    }

    // Around advices method exection time,target invocation,ctler jp.proceed()
    @Around("serviceLayerPointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // proceed with target invocation
        Object result = joinPoint.proceed();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Aspect OP:" + joinPoint.getSignature().toShortString() + "executed in" + duration + "ms.");
        return result;
    }

}
