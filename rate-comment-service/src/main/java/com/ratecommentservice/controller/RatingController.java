package com.ratecommentservice.controller;


import com.ratecommentservice.model.Rate;
import com.ratecommentservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class RatingController {

    private RateService rateService;
    @Autowired
    public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }


    //TODO do we really need book's all rates
    @GetMapping("/{bookId}")
    public List<Rate> getBookRates(@PathVariable("bookId") String  bookId ){

        return rateService.getBookRates(bookId);


    }

    @GetMapping("/rate/book/{bookId}")
    public Double getBookRate(@PathVariable("bookId") String  bookId ){
        List<Integer> rates = rateService.getBookRates(bookId).stream()
                .map(p -> p.getRate())
                .collect(Collectors.toList());
        return rates.stream().mapToDouble(a -> a)
                .average().getAsDouble();


    }


    @GetMapping("/rate/user")
    public List<Rate> getUserRates( OAuth2Authentication auth) {
        return rateService.getUserRates(auth.getName());


    }
}

