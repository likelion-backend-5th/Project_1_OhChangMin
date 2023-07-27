package com.mutsa.mutsamarket.exception;

public class NotAllowResponseException extends CustomException {

    private static final String MESSAGE = "제안상태가 아니기 떄문에 응답 할 수 없습니다.";

    public NotAllowResponseException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
