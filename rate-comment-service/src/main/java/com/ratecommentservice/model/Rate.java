package com.ratecommentservice.model;

import javax.persistence.*;


@Entity
@Table(name ="rate")
public class Rate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rateId;
    private String bookId;
    private Long userId;

    private int rate;


    public Rate(String bookId, Long userId, int rate) {
        this.bookId = bookId;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
