package ru.chuvashov.course.lesson14.hw14.account.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class AccountServiceImplAspect {

    @Around("execution(public * ru.chuvashov.course.lesson14.hw14.account.service.*.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info(joinPoint.getSignature().toString());
        return joinPoint.proceed();
    }
}
