package com.mutsa.mutsamarket.exception;

public class ItemMismatchedException extends CustomException {

    private static final String MESSAGE = "아이템 정보가 일치하지 않습니다.";

    public ItemMismatchedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
