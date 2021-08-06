package ru.chuvashov.course.lesson14.hw14.account.aspect;


import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Log
@Component
@Aspect
public class RepositoryLogAspect {

    @After("execution(* ru.chuvashov.course.lesson14.hw14.account.repository.*.*(..))")
    public void printLog(JoinPoint jp) {
        log.info(jp.getSignature().toString());
    }
}
