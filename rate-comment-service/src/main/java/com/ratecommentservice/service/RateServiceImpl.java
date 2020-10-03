package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;
import com.ratecommentservice.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RateServiceImpl implements RateService {
    private RateRepository rateRepository;
    @Autowired
    public void setRateRepository(RateRepository rateRepository){this.rateRepository=rateRepository;}

    @Override
    public List<Rate> getBookRates(String bookID) {
        return rateRepository.findByBookId(bookID);
    }

    @Override
    public List<Rate> getUserRates(String username) {
        return rateRepository.findByUsername(username);
    }
}
