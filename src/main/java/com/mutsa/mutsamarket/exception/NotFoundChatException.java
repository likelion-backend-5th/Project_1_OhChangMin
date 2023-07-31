package com.mutsa.mutsamarket.exception;

public class NotAllowConfirmException extends CustomException {

    private static final String MESSAGE = "수락상태가 아니기 떄문에 확정 할 수 없습니다.";

    public NotAllowConfirmException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
