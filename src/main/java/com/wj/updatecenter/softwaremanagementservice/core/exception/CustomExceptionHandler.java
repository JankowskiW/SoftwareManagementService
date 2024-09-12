package com.wj.updatecenter.softwaremanagementservice.core.exception;

import com.wj.shared.definition.RequestValidationException;
import com.wj.shared.definition.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ExceptionResponseBody> handleRequestValidationException(RequestValidationException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseBody> handleResourceNotFoundException(ResourceNotFoundException e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponseBody> handleIllegalArgumentException(IllegalArgumentException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponseBody> handleException(RuntimeException e, HttpStatus httpStatus) {
        ExceptionResponseBody body = new ExceptionResponseBody(
                httpStatus,
                e.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(body, httpStatus);
    }
}
