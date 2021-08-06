package ru.chuvashov.course.lesson14.hw14.account.aspect;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.chuvashov.course.lesson14.hw14.account.model.Account;

import java.util.HashMap;

@Component
@Aspect
@Log
public class CacheBalanceAspect {

    private final HashMap<Integer, Integer> balanceMap = new HashMap<>();

    @Around("@annotation(ru.chuvashov.course.lesson14.hw14.account.annotation.CachePutBalance)")
    public void putValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object arg = joinPoint.getArgs()[0];
        joinPoint.proceed();
        if (arg instanceof Account) {
            Account account = (Account) joinPoint.getArgs()[0];
            balanceMap.put(account.getId(), account.getAmount());
            log.info("value loaded in the cache: " + account.getAmount());
        }
    }

    @Around("@annotation(ru.chuvashov.course.lesson14.hw14.account.annotation.CacheGetBalance)")
    public int getValue(ProceedingJoinPoint jp) throws Throwable {
        int id = (int) jp.getArgs()[0];
        int balance;
        if (balanceMap.get(id) != null) {
            balance = balanceMap.get(id);
            log.info("Value loaded from cache: " + balance);
            return balance;
        }
        balance = (int) jp.proceed();
        balanceMap.put(id, balance);
        return balance;
    }
}
