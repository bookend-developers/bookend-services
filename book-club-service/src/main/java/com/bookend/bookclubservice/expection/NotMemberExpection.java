package com.bookend.bookclubservice.expection;

import org.hibernate.jdbc.Expectation;

public class NotMemberExpection extends Exception {

    public NotMemberExpection(String message) {
        super(message);
    }
}
