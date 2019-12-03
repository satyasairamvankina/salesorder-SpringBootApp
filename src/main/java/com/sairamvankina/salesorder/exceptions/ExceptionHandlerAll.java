package com.sairamvankina.salesorder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.net.http.HttpConnectTimeoutException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerAll {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<SalesResponse> validationExceptionHandler(Exception ex) {
        return new ResponseEntity<>(new SalesResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<SalesResponse> nullPointerExceptionHandler(Exception ex){
        return new ResponseEntity<>(new SalesResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpConnectTimeoutException.class,SQLException.class})
    public ResponseEntity<SalesResponse> databaseConnectionException(Exception ex){
        return new ResponseEntity<>(new SalesResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
