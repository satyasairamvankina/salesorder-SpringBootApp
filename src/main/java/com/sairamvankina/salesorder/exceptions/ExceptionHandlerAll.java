package com.sairamvankina.salesorder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.net.http.HttpConnectTimeoutException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerAll {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SalesResponse> validationExceptionHandler(Exception ex) {
        System.out.println("ExceptionHandlerAll: Validation exception handled");
        return new ResponseEntity<>(new SalesResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

//   HANDLED BETTER IN DEFAULT VALIDATION EXCEPTION HANDLER
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<SalesResponse> validationExceptionHandlerMethodArg(Exception ex) {
//        System.out.println("ExceptionHandlerAll: Validation exception handling MethodArgumentNotValidException");
//        return new ResponseEntity<>(new SalesResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
//    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<SalesResponse> nullPointerExceptionHandler(Exception ex){
        System.out.println("ExceptionHandlerAll: Null pointer exception handled");
        return new ResponseEntity<>(new SalesResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpConnectTimeoutException.class,SQLException.class})
    public ResponseEntity<SalesResponse> databaseConnectionException(Exception ex){
        return new ResponseEntity<>(new SalesResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
