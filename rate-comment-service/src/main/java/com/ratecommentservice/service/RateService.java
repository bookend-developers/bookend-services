package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.RateRequest;

import java.util.List;

public interface RateService {

    List<Rate> getUserRates(String username);
    Double getBookAverageRate(String bookID);
    Rate save(RateRequest rateRequest,String username);
    void deleteRate(Rate rate);
    void deleteRateByBookId(String bookId);
    Rate findByRateID(Long rateId);
    Rate findRateByBookIdandUsername(String bookId, String username);
}
