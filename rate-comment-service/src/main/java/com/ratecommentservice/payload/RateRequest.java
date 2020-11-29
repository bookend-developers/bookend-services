package com.ratecommentservice.payload;

public class RateRequest {
    private Double rate;
    private String bookId;
    private String bookname;

    public RateRequest(Double rate, String bookId, String bookname) {
        this.rate = rate;
        this.bookId = bookId;
        this.bookname = bookname;
    }

    public RateRequest() {
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
