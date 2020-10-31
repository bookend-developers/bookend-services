package com.ratecommentservice.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


@Entity
@Table(name ="rate")
public class Rate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    private Long rateId;
    @Column(name = "bookId")
    @ApiModelProperty(notes = "The specific bookId that rate belongs to")
    private String bookId;
    @Column(name = "username")
    @ApiModelProperty(notes = "The specific userId that rate is written by")
    private String username;
    @ApiModelProperty(notes = "The value of the rate")
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
