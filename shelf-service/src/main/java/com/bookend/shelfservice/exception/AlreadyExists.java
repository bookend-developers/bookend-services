package com.bookend.shelfservice.exception;

public class AlreadyExists extends Exception{
    public AlreadyExists() {
    }

    public AlreadyExists(String message) {
        super(message);
    }
}
