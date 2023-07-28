package com.mutsa.mutsamarket.exception;

public class AlreadySoldOutException extends CustomException {

    private static final String MESSAGE = "이미 판매 된 상품입니다.";

    public AlreadySoldOutException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
