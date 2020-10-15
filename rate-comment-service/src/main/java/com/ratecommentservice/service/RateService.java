package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;

import java.util.List;

public interface RateService {
    List<Rate> getBookRates(String bookID);
    List<Rate> getUserRates(String username);
    Double getBookAverageRate(String bookID);
    Rate save(Rate newRate);
    void deleteRate(Long rateId);
}
