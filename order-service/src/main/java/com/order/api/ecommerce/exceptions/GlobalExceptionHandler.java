package com.order.api.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(NotFoundException ex, WebRequest request){
        ErrorObject err = new ErrorObject();
        err.setStatus(HttpStatus.NOT_FOUND.value());
        err.setMessage(err.getMessage());
        err.setDateTimeStamp(new Date());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

}
