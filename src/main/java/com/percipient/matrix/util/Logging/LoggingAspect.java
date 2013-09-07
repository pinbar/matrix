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

    @Pointcut("within (com.percipient.matrix.util.Logging.LoggingAspect)")
    public void methodsInLoggingAspect() {
    }

    @Pointcut("within (com.percipient.matrix.session.UserInfo)")
    public void methodsInUserInfo() {
    }

    @Before("methodsToBeLogged() && !methodsInUserInfo() && !methodsInLoggingAspect()")
    public void displayBefore(JoinPoint jp) {

        log.info("Entering method : " + jp.getSignature().getName());
        Object[] args = jp.getArgs();
        log.info("With Arguments : " + args);

        log.info("With Arguments : ======>");
        for (int i = 0; i < args.length; i++) {
            // log.info("Arg: " + args[i]);
            // System.out.println("Arg: " + args[i]);

        }

    }

    @After("execution(public * com.percipient.matrix..*(..))")
    public void displayAfter(JoinPoint jp) {
        log.info("Returning from : " + jp.getSignature().getName());

    }

    @AfterThrowing(pointcut = "execution(* com.percipient.matrix..*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint jp, Throwable error) {
        log.error("Error in  : " + jp.getSignature().getName(), error);
    }
}
