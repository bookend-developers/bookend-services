package com.bookend.shelfservice.exception;

public class ShelfNotFound extends Exception{
    public ShelfNotFound() {
    }

    public ShelfNotFound(String message) {
        super(message);
    }
}
