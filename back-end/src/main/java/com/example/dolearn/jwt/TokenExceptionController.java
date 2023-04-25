package com.example.dolearn.jwt;

import com.example.dolearn.exception.error.ErrorCode;
import com.example.dolearn.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenExceptionController {
    
    @GetMapping("/exception/token")
    public ResponseEntity<?> tokenException() {
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.INVALID_TOKEN), HttpStatus.FORBIDDEN);
    }
}