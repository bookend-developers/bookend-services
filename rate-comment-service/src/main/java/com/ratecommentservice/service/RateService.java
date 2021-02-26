package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.RateNotFound;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.RateRequest;

import java.util.List;

public interface RateService {

    List<Rate> getUserRates(String username);
    Double getBookAverageRate(String bookID) throws BookNotFound, RateNotFound;
    Rate save(RateRequest rateRequest,String username);
    void deleteRate(Rate rate);
    void deleteRateByBookId(String bookId);
    Rate findByRateID(Long rateId) throws RateNotFound;
    Rate findRateByBookIdandUsername(String bookId, String username) throws BookNotFound, RateNotFound;

}
