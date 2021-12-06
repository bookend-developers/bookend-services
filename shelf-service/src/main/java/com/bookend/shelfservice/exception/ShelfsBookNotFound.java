package com.bookend.shelfservice.exception;

public class ShelfsBookNotFound extends Exception{
    public ShelfsBookNotFound() {
    }

    public ShelfsBookNotFound(String message) {
        super(message);
    }
}
