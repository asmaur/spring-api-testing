package com.wanubit.springtesting.exceptions;

import com.wanubit.springtesting.dto.ErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleValidationExceptions(ConstraintViolationException ex){
        List<String> details = new ArrayList<>();

        for(ConstraintViolation<?> error : ex.getConstraintViolations()){
            details.add(error.getMessageTemplate());
        }
        ErrorResponse error = new ErrorResponse("Validation failed.", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
