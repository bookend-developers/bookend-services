package com.ratecommentservice.model;

import javax.persistence.*;


@Entity
@Table(name ="rate")
public class Rate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rateId;
    @Column(name = "bookId")
    private String bookId;
    @Column(name = "username")
    private String username;
    @Column(name = "rate")
    private Double rate;


    public Rate(String bookId, String username, Double rate) {
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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
