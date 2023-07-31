package com.mutsa.mutsamarket.exception;

public class NotFoundChatException extends CustomException {

    private static final String MESSAGE = "채팅을 찾을 수 없습니다.";

    public NotFoundChatException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
