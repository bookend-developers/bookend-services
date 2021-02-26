package com.ratecommentservice.exception;

public class BookNotFound extends Exception{
    public BookNotFound() {
    }

    public BookNotFound(String message) {
        super(message);
    }
}
