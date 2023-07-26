package com.mutsa.mutsamarket.advice;

import com.mutsa.mutsamarket.exception.CustomException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionMessage> customExceptionHandler(CustomException e) {
        return  ResponseEntity
                .status(e.getStatusCode())
                .body(new ExceptionMessage(e.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ExceptionMessage> requestDataExceptionHandler() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionMessage("요청 데이터의 형식이 올바르지 않습니다."));
    }

    @Data
    private static class ExceptionMessage {

        private String exceptionMessage;

        public ExceptionMessage(String exceptionMessage) {
            this.exceptionMessage = exceptionMessage;
        }
    }
}

