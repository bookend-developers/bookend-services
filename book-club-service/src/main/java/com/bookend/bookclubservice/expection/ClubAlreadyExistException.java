package com.bookend.bookclubservice.expection;

public class ClubAlreadyExistException extends RuntimeException {

    public ClubAlreadyExistException(String message) {
        super(message);
    }
}
