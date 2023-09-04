package com.jeonsee.jeonseerestapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.security.InvalidParameterException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { DocumentNotFoundException.class })
    public ResponseEntity<String> handleDocumentNotFoundException(DocumentNotFoundException ex) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ExceedLimitOfAlarmException.class })
    public ResponseEntity<String> handleExceedLimitOfAlarmException(ExceedLimitOfAlarmException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { ConstraintViolationException.class })
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { InvalidParameterException.class })
    public ResponseEntity<String> handleInvalidParameterException(InvalidParameterException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}

