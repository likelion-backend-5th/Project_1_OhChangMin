package com.mutsa.mutsamarket.exception;

public class NotAllowItemSellerProposalException extends CustomException {

    private static final String MESSAGE = "아이템 판매자는 제안을 등록할 수 없습니다.";

    public NotAllowItemSellerProposalException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
