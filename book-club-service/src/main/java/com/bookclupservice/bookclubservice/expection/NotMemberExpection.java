package com.bookclupservice.bookclubservice.expection;

import org.hibernate.jdbc.Expectation;

public class NotMemberExpection extends Exception {

    public NotMemberExpection() {
    }

    public NotMemberExpection(String message) {
        super(message);
    }
}
