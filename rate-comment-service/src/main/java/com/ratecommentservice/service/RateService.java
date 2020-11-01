package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;

import java.util.List;

public interface RateService {

    List<Rate> getUserRates(String username);
    Double getBookAverageRate(String bookID);
    Rate save(Rate newRate);
    void deleteRate(Rate rate);
    void deleteRateByBookId(String bookId);
    Rate findByRateID(Long rateId);
    Rate findRateByBookIdandUsername(String bookId, String username);
}
