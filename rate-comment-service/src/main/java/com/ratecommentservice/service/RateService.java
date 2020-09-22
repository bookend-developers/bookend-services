package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;

import java.util.List;

public interface RateService {
    List<Rate> getBookRates(Long bookID);
    List<Rate> getUserRates(Long userID);
}
