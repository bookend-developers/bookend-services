package com.bookend.shelfservice.exception;

public class NotFoundException extends Exception{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}
