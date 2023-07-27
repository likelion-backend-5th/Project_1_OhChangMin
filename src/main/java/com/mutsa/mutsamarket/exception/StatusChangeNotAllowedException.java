package com.mutsa.mutsamarket.exception;

public class StatusChangeNotAllowedException extends CustomException {

    private static final String MESSAGE = "제안상태가 아니기 떄문에 상태를 바꿀 수 없습니다.";

    public StatusChangeNotAllowedException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
