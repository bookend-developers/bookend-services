package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.model.Book;

import java.util.List;
/**
 * RCS-BSC stands for RateCommentService-BookServiceClass
 * SM stands for ServiceMethod
 */
public interface BookService {
    /**
     * RCS-BSC-1 (SM_60)
     */
    Book save(Book book);
    /**
     * RCS-BSC-2 (SM_61)
     */
    Book findBookByBookID( String bookid) throws BookNotFound;
    /**
     * RCS-BSC-3 (SM_62)
     */
    List<String> findAll();
}
