package com.example.dolearn.exception;

import com.example.dolearn.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class InputValidationHandler {

    // Dto 유효성 검증
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleDtoNotValid(MethodArgumentNotValidException e) {
        e.printStackTrace();
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, errorMessage), HttpStatus.METHOD_NOT_ALLOWED);
    }

    // Path variable 유효성 검증
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlePathVariableNotValid(ConstraintViolationException e) {
        e.printStackTrace();
        String message = e.getMessage();
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, message), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
