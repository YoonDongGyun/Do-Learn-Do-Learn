package com.example.dolearn.response;

import com.example.dolearn.exception.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    
    private HttpStatus httpStatus;
    private String code;
    private Object response;

    public ErrorResponse(ErrorCode code) {
        this.httpStatus = code.getHttpStatus();
        this.code = code.getCode();
        this.response = code.getMessage();
    }

    public ErrorResponse(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.code = String.valueOf(httpStatus.value());
        this.response = errorMessage;
    }
}
