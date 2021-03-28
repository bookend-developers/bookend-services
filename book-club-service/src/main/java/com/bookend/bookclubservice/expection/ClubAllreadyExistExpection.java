package com.bookend.bookclubservice.expection;

public class ClubAllreadyExistExpection extends RuntimeException {

    public ClubAllreadyExistExpection(String message) {
        super(message);
    }
}
