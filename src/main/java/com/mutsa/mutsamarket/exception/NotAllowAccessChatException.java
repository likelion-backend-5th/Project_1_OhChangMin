package com.mutsa.mutsamarket.exception;

public class NotAllowAccessChatException extends CustomException {

    private static final String MESSAGE = "접근 할 수 없는 채팅방입니다.";

    public NotAllowAccessChatException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
