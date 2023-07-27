package com.mutsa.mutsamarket.exception;

public class NotFoundItemException extends CustomException {

    private static final String MESSAGE = "해당 아이템이 존재하지 않습니다.";

    public NotFoundItemException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
