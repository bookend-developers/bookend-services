package com.ratecommentservice.service;

import com.ratecommentservice.model.Rate;
import com.ratecommentservice.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    private RateRepository rateRepository;
    @Autowired
    public void setRateRepository(RateRepository rateRepository){this.rateRepository=rateRepository;}



    @Override
    public List<Rate> getUserRates(String username) {

        return rateRepository.findByUsername(username);
    }

    @Override
    public Double getBookAverageRate(String bookID) {
        List<Double> rates = rateRepository.findByBookId(bookID).stream()
                .map(p -> p.getRate())
                .collect(Collectors.toList());
        return rates.stream().mapToDouble(a -> a)
                .average().getAsDouble();
    }

    @Override
    public Rate save(Rate newRate) {
        return rateRepository.save(newRate);
    }

    @Override
    public void deleteRate(Long rateId) {
        rateRepository.deleteById(rateId);
    }
}
