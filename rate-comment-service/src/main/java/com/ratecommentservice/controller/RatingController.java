package com.ratecommentservice.controller;


import com.ratecommentservice.model.Rate;
import com.ratecommentservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/rate")
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

    @GetMapping("/book/{bookId}")
    public Double getBookRate(@PathVariable("bookId") String  bookId ){
        return rateService.getBookAverageRate(bookId);


    }

    @GetMapping("/user")
    public List<Rate> getUserRates( OAuth2Authentication auth) {
        return rateService.getUserRates(auth.getName());


    }
    @PostMapping("/new/{bookid}")
    public Rate rateBook(OAuth2Authentication auth
            ,@PathVariable("bookid") String  bookId
            ,@RequestParam("rate") Double rate){
        //Rate newRate = new Rate(bookId,auth.getName(),rate);
        return rateService.save( new Rate(bookId,auth.getName(),rate));

    }
    @DeleteMapping("/delete/{rateid}")
    public void deleteRate(@PathVariable("rateid") String rateId){
        rateService.deleteRate(Long.valueOf(rateId));
    }
}

