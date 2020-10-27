package com.ratecommentservice.controller;


import com.ratecommentservice.model.Rate;
import com.ratecommentservice.service.RateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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



    @ApiOperation(value = "Get book's average rate value ", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved rate"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/book/{bookId}")
    public Double getBookRate(@PathVariable("bookId") String  bookId ){
        Double rate= rateService.getBookAverageRate(bookId);
        if(rate == null){
            return 0.0;
        }
        return rate;


    }
    @ApiOperation(value = "View user's rates ", response = Rate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    }
    )
    @GetMapping("/user")
    public List<Rate> getUserRates( OAuth2Authentication auth) {

        return rateService.getUserRates(auth.getName());


    }
    @ApiOperation(value = "Rate a specific book", response = Rate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully rating the book"),
            @ApiResponse(code = 401, message = "You are not authorized to rate the boook"),
            @ApiResponse(code = 400, message = "The way you are trying to rate is not accepted.")
    }
    )
    @PostMapping("/new/{bookid}")
    public Rate rateBook(OAuth2Authentication auth
            ,@PathVariable("bookid") String  bookId
            ,@RequestParam("rate") Double rate){
        if(rate== null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please fill all necessary places");
        }
        return rateService.save( new Rate(bookId,auth.getName(),rate));

    }
    @ApiOperation(value = "Delete a specific rate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted rate"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/delete/{rateid}")
    public void deleteRate(@PathVariable("rateid") String rateId){
        rateService.deleteRate(Long.valueOf(rateId));
    }
}

