package com.bookend.authorservice.exception;

public class AuthorNotFound  extends  Exception{
    public AuthorNotFound() {
    }

    public AuthorNotFound(String message) {
        super(message);
    }
}
