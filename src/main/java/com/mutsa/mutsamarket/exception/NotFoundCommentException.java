package com.mutsa.mutsamarket.exception;

import com.mutsa.mutsamarket.exception.CustomException;

public class NotFoundCommentException extends CustomException {

    private static final String MESSAGE = "해당 댓글이 존재하지 않습니다.";

    public NotFoundCommentException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
