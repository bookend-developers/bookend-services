package com.bookend.authorservice.exception;

public class AuthorAlreadyExists extends  Exception{
    public AuthorAlreadyExists() {
    }

    public AuthorAlreadyExists(String message) {
        super(message);
    }
}
