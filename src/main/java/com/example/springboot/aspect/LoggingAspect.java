package com.example.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging(){}

    @Before("executeLogging()")
    public void logMethodExecution(JoinPoint jointPoint){
        StringBuilder message = new StringBuilder("Method: ");
        message.append(jointPoint.getSignature().getName());
        LOGGER.info(message.toString());
    }

    @AfterReturning(value = "executeLogging()", returning = "returnValue")
    public void logMethodReturn(JoinPoint jointPoint, Object returnValue){
        StringBuilder message = new StringBuilder("After returning from: ");
        message.append(jointPoint.getSignature().getName());
        if (returnValue instanceof Collection){
            message.append(" Return value::" + ((Collection<?>) returnValue).size());
        }else{
            message.append(" Return value::" + returnValue.toString());
        }
        LOGGER.info(message.toString());
    }
}
