package com.mutsa.mutsamarket.exception;

public class NotFoundProposalException extends CustomException {

    private static final String MESSAGE = "해당 제안이 존재하지 않습니다.";

    public NotFoundProposalException() {
        super(MESSAGE);
    }

    public NotFoundProposalException(UserMismatchedException e) {
        super(MESSAGE, e);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
