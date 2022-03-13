package com.ratecommentservice.exception;

public class CommentNotFound extends Exception {
    public CommentNotFound() {
    }

    public CommentNotFound(String message) {
        super(message);
    }
}
