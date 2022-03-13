package com.bookend.shelfservice.exception;

public class TagNotFound extends Exception{
    public TagNotFound() {
    }

    public TagNotFound(String message) {
        super(message);
    }
}
