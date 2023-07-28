package com.mutsa.mutsamarket.exception;

public class NotAllowAcceptCancelException extends CustomException {

    private static final String MESSAGE = "확정 상태의 제안은 삭제 할 수 없습니다.";

    public NotAllowAcceptCancelException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
