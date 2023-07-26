package com.mutsa.mutsamarket.exception;

public class PasswordMismatchException extends CustomException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordMismatchException() {
        super(MESSAGE);
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
