package com.ratecommentservice.payload;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
import lombok.Getter;

public class RateResponse {

    @Getter
    private Long rateId;
    @Getter
    private Book book;
    @Getter
    private String username;
    @Getter
    private Double rate;

    public RateResponse(Rate rate) {
        this.rateId = rate.getRateId();
        this.book = rate.getBookId();
        this.username = rate.getUsername();
        this.rate = rate.getRate();
    }
}
