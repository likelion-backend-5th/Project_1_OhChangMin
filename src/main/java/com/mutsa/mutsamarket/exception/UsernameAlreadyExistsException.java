package com.mutsa.mutsamarket.exception;

public class UsernameAlreadyExistsException extends CustomException {

    private static final String MESSAGE = "이미 존재하는 이메일입니다.";

    public UsernameAlreadyExistsException() {
        super(MESSAGE);
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
