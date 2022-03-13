package com.bookend.bookservice.exception;

public class AlreadyExist  extends Exception{
    public AlreadyExist() {
    }

    public AlreadyExist(String message) {
        super(message);
    }
}
