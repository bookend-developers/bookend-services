package com.ratecommentservice.model;

import javax.persistence.*;


@Entity
@Table(name ="rate")
public class Rate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rateId;
    private String bookId;
    private String username;

    private int rate;


    public Rate(String bookId, String username, int rate) {
        this.bookId = bookId;
        this.username = username;
        this.rate = rate;
    }

    public Rate(){}

    public Long getRateId() {
        return rateId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userId) {
        this.username = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
