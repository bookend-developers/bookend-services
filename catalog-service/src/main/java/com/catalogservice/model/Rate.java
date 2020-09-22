package com.catalogservice.model;

public class Rate {
    private Long rateId;
    private Long bookId;
    private Long userId;

    private int rate;


    public Rate() {
    }

    public Rate(Long rateId, Long bookId, Long userId, int rate) {
        this.rateId = rateId;
        this.bookId = bookId;
        this.userId = userId;
        this.rate = rate;

    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
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
