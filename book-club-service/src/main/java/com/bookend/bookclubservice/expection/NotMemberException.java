package com.bookend.bookclubservice.expection;

import org.hibernate.jdbc.Expectation;

public class NotMemberException extends Exception {

    public NotMemberException(String message) {
        super(message);
    }
}
