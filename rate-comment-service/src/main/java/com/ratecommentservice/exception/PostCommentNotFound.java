package com.ratecommentservice.exception;

public class PostCommentNotFound extends Exception{
    public PostCommentNotFound() {
    }

    public PostCommentNotFound(String message) {
        super(message);
    }
}
