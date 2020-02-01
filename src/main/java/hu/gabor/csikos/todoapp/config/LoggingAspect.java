package hu.gabor.csikos.todoapp.config;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log
public class LoggingAspect {

    @Pointcut("within(hu.gabor.csikos.todoapp.controller..*)")
    public void logController() {
    }

    @Around("logController()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object toReturn = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Time Taken by :" + joinPoint + " is :" + timeTaken);
        return toReturn;//Keep all data
    }

}