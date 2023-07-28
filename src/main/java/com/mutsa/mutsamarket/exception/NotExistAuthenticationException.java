package com.mutsa.mutsamarket.exception;

public class NotExistAuthenticationException extends CustomException {

    private static final String MESSAGE = "Authentication 객체가 존재하지 않습니다.";

    public NotExistAuthenticationException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
