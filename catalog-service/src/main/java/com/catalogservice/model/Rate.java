package com.catalogservice.model;

public class Rate {
    private Long rateId;
    private String bookId;
    private String username;

    private int rate;


    public Rate() {
    }

    public Rate(Long rateId, String bookId, String username, int rate) {
        this.rateId = rateId;
        this.bookId = bookId;
        this.username = username;
        this.rate = rate;

    }

    public Long getRateId() {
        return rateId;
    }

    public void setRateId(Long rateId) {
        this.rateId = rateId;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

}
