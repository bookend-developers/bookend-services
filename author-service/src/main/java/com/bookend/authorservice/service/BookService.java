package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Book;
/**
 * AS-BSC stands for AuthorService-BookServiceClass
 * SM stands for ServiceMethods
 */
public interface BookService {
    /**
     * AS-BSC-1 (SM_6)
     */
    Book findByBookid(String bookId) throws NotFoundException;
    /**
     * AS-BSC-2 (SM_7)
     */
    Book save(Book book) throws MandatoryFieldException;
    /**
     * AS-BSC-3 (SM_8)
     */
    void deleteByBookId(String bookId) throws NotFoundException;
}
