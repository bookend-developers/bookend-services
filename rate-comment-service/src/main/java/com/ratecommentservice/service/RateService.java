package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.RateNotFound;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.RateRequest;

import java.util.List;
/**
 * RCS-RSC stands for RateCommentService-RateServiceClass
 * SM stands for ServiceMethod
 */
public interface RateService {
    /**
     * RCS-RSC-1 (SM_71)
     */
    List<Rate> getUserRates(String username);
    /**
     * RCS-RSC-2 (SM_72)
     */
    Double getBookAverageRate(String bookID) throws BookNotFound, RateNotFound;
    /**
     * RCS-RSC-3 (SM_73)
     */
    Rate save(RateRequest rateRequest,String username);
    /**
     * RCS-RSC-4 (SM_74)
     */
    void deleteRate(Rate rate);
    /**
     * RCS-RSC-5 (SM_75)
     */
    void deleteRateByBookId(String bookId);
    /**
     * RCS-RSC-6 (SM_76)
     */
    Rate findByRateID(Long rateId) throws RateNotFound;
    /**
     * RCS-RSC-7 (SM_77)
     */
    Rate findRateByBookIdandUsername(String bookId, String username) throws BookNotFound, RateNotFound;


}
