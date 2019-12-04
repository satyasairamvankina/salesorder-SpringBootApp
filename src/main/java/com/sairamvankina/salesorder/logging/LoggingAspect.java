package com.sairamvankina.salesorder.logging;

import com.mysql.cj.log.LogFactory;
import com.sairamvankina.salesorder.exceptions.ResourceBadRequestException;
import com.sairamvankina.salesorder.exceptions.ResourceNotFoundException;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;

@Aspect
@Component
public class LoggingAspect {

    @AfterThrowing(pointcut = "execution(* com.sairamvankina.salesorder.service.*.*(..))",throwing = "exception")
    public void logExceptionForRepository(ResourceBadRequestException exception) throws ResourceBadRequestException{
        log(exception);
    }


    @AfterThrowing(pointcut = "execution(* com.sairamvankina.salesorder.service.*.*(..))",throwing = "exception")
    public void logExceptionForServiceNotFound(ResourceNotFoundException exception) throws ResourceNotFoundException{
        log(exception);
    }
    @AfterThrowing(pointcut = "execution(* com.sairamvankina.salesorder.service.*.*(..))",throwing = "exception")
    public void logExceptionForServiceConstaint(ConstraintViolationException exception) throws ConstraintViolationException{
        log(exception);
    }

    @AfterThrowing(pointcut = "execution(* com.sairamvankina.salesorder.logging.*.*(..))",throwing = "exception")
    public void logExceptionForServiceMethodArg(MethodArgumentNotValidException exception) throws MethodArgumentNotValidException{
        log(exception);
    }

    @AfterThrowing(pointcut = "execution(* com.sairamvankina.salesorder.logging.*.*(..))",throwing = "exception")
    public void logExceptionForServiceNullPointer(NullPointerException exception) throws NullPointerException{
        log(exception);
    }

    @AfterReturning(pointcut = "execution(* com.sairamvankina.salesorder.service.SalesOrderServiceInterfaceImpl.createOrder(..))")
    public void creteOrderLogs(){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.info("Logger Aspect: "+ "created sales order");
    }

    public void log(Exception exception){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.error("Logger Aspect: "+exception.getMessage(),exception);
    }
}
