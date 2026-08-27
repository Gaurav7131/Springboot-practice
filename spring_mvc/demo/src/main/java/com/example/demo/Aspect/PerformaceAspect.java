package com.example.demo.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformaceAspect {

    // Pointcut:predicate exp matches joinPoint.(method execution for spring aop)
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceLayerPointcut() {
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
