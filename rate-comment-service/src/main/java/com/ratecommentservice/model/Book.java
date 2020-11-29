package com.ratecommentservice.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="bookId")
    private String bookId;
    @Column(name="bookname")
    private String bookname;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,
            fetch = FetchType.LAZY,mappedBy = "book")
    private List<Rate> rates;
    private Double averageRate;
    @Column
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    private List<Comment> comments;

    public Book() {
    }

    public Book(String bookId, String bookname) {
        this.bookId = bookId;
        this.bookname = bookname;
        this.comments = new ArrayList<>();
        this.rates = new ArrayList<>();
    }

    public Double getAverageRate() {
        return averageRate;
    }



    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Long getId() {
        return id;
    }


    public String getBookid() {
        return bookId;
    }

    public void setBookid(String bookid) {
        this.bookId = bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
    @JsonIgnore
    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
    @JsonIgnore
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public Double calAv(){

        List<Double> rates = this.getRates().stream()
                .map(p -> p.getRate())
                .collect(Collectors.toList());
        if(rates==null){
            return 0.0;
        }
        return rates.stream().mapToDouble(a -> a)
                .average().getAsDouble();
    }
}
