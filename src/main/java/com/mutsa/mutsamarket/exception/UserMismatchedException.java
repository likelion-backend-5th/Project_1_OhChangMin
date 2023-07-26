package com.mutsa.mutsamarket.exception;

public class UserMismatchedException extends CustomException {

    private static final String MESSAGE = "유저 정보가 일치하지 않습니다.";

    public UserMismatchedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
