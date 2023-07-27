package com.mutsa.mutsamarket.exception;

public class FileIsEmptyException extends CustomException {

    private static final String MESSAGE = "요청 파일이 존재하지 않습니다.";

    public FileIsEmptyException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
