package com.ratecommentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


@Entity
@Table(name ="rate")
public class Rate {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiModelProperty(notes = "The database generated product ID")
    private Long rateId;
    @ApiModelProperty(notes = "The specific book that rate belongs to")

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "username")
    @ApiModelProperty(notes = "The specific userId that rate is written by")
    private String username;
    @ApiModelProperty(notes = "The value of the rate")
    @Column(name = "rate")
    private Double rate;


    public Rate(Book book, String username, Double rate) {
        this.book = book;
        this.username = username;
        this.rate = rate;
    }

    public Rate(){}

    public Long getRateId() {
        return rateId;
    }

    public Book getBookId() {
        return book;
    }

    public void setBookId(Book book) {
        this.book = book;
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
