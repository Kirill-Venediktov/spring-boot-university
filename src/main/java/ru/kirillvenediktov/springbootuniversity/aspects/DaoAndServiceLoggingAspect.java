package ru.kirillvenediktov.springbootuniversity.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kirillvenediktov.springbootuniversity.enums.LoggerConstant;


@Component
@Aspect
public class DaoAndServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(DaoAndServiceLoggingAspect.class);

    @Pointcut("execution(* ru.kirillvenediktov.springbootuniversity.dao.*.*(..))")
    private void daoMethodsPointcut() {
    }

    @Pointcut("execution(* ru.kirillvenediktov.springbootuniversity.service.*.*(..))")
    private void serviceMethodsPointcut() {
    }

    @Around("daoMethodsPointcut() || serviceMethodsPointcut()")
    public Object aroundAllDAOMethodsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();
        logger.debug(LoggerConstant.METHOD_STARTED.getValue(), methodName);
        Object targetMethodResult = proceedingJoinPoint.proceed();
        logger.debug(LoggerConstant.METHOD_FINISH.getValue(), methodName);
        logger.debug(LoggerConstant.METHOD_RESULT.getValue(), targetMethodResult);
        return targetMethodResult;
    }

}
