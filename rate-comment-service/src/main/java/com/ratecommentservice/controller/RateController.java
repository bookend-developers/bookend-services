package com.ratecommentservice.controller;


import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.RateNotFound;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.MessageResponse;
import com.ratecommentservice.payload.RateRequest;
import com.ratecommentservice.service.BookService;
import com.ratecommentservice.service.RateService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RCS-RC stands for RateCommentService-RateController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/rate")
public class RateController {

    private RateService rateService;
    @Autowired
    public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * RCS-RC-1 (CM_48)
     */
    @ApiOperation(value = "Get book's average rate value ", response = Double.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved rate"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/book/{bookId}")
    public Double getBookRate(@PathVariable("bookId") String  bookId ) throws BookNotFound, RateNotFound {
        Double rate= rateService.getBookAverageRate(bookId);
        if(rate == null){
            return 0.0;
        }
        return rate;


    }
    /**
     * RCS-RC-2 (CM_49)
     */
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
    /**
     * RCS-RC-3 (CM_50)
     */
    @ApiOperation(value = "Rate a specific book", response = Rate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully rating the book"),
            @ApiResponse(code = 401, message = "You are not authorized to rate the boook"),
            @ApiResponse(code = 400, message = "The way you are trying to rate is not accepted.")
    }
    )
    @PostMapping("/new")
    public Rate rateBook(OAuth2Authentication auth
            ,@RequestBody RateRequest rateRequest){
        if(rateRequest.getRate()== null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please fill all necessary places");
        }
        if(rateRequest.getBookId()== null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to rate is not accepted.");
        }
        if(rateRequest.getBookname()== null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to rate is not accepted.");
        }



        return rateService.save(rateRequest,auth.getName());

    }
    /**
     * RCS-RC-4 (CM_51)
     */
    @ApiOperation(value = "Delete a specific rate")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted rate"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The operation forbidden.")
    }
    )
    @DeleteMapping("/delete/{rateid}")
    public ResponseEntity<?> deleteRate(@PathVariable("rateid") String rateId, OAuth2Authentication auth) throws RateNotFound {
        Rate rate =rateService.findByRateID(Long.valueOf(rateId));
        if(rate==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found.");
        }
        if(!rate.getUsername().equals(auth.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The operation you trying to do is forbidden.");
        }
        rateService.deleteRate(rate);
        return ResponseEntity.ok(new MessageResponse("Rate deleted successfully."));
    }
    /**
     * RCS-RC-5 (CM_52)
     */
    @GetMapping("/sort/")
    public List<String> listBookIDs(){
        return bookService.findAll();
    }
}

