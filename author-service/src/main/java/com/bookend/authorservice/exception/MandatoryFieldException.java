package com.bookend.authorservice.exception;

public class MandatoryFieldException extends  Exception{
    public MandatoryFieldException() {
    }

    public MandatoryFieldException(String message) {
        super(message);
    }
}
