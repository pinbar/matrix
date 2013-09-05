package com.percipient.matrix.util.Logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger log = LoggerFactory
            .getLogger(LoggingAspect.class);

    @Pointcut("execution(public * com.percipient.matrix..*(..))")
    public void methodsToBeLogged() {
    }

    @Before("execution(public * com.percipient.matrix..*(..))")
    public void displayBefore(JoinPoint jp) {
        log.info("Entering method : " + jp.getSignature().getName());
        log.info("With Arguments : " + jp.getArgs());
    }

    @After("execution(public * com.percipient.matrix..*(..))")
    public void displayAfter(JoinPoint jp) {
        log.info("Returning from : " + jp.getSignature().getName());

    }

    @AfterThrowing(pointcut = "execution(* com.percipient.matrix..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint jp, Throwable error) {
        log.error("Error in  : " + jp.getSignature().getName(), error);
    }

    /*
     * @AfterReturning(pointcut = "execution(* com.percipient.matrix..*(..))",
     * returning = "retval") public void displayAfterReturning(JoinPoint jp,
     * Object retval) { log.info("After Returning Advice by " +
     * jp.getSignature().getName() + " and returned the value: " + retval);
     * 
     * }
     */
}
