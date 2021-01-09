package com.bookclupservice.bookclubservice.expection;

public class ClubAllreadyExistExpection extends RuntimeException {
    public ClubAllreadyExistExpection() {
        super();
    }

    public ClubAllreadyExistExpection(String message) {
        super(message);
    }
}
