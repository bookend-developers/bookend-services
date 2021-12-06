package com.bookend.bookservice.service;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.payload.BookRequest;

import java.util.List;
/**
 * BS-BSC stands for BookService-BookServiceClass
 * SM stands for ServiceMethod
 */
public interface BookService {
    /**
     * BS-BSC-1 (SM_31)
     */
    Book getById(String id) throws NotFoundException;
    /**
     * BS-BSC-2 (SM_32)
     */
    Book save(BookRequest bookRequest) throws MandatoryFieldException, AlreadyExist;
    /**
     * BS-BSC-3 (SM_33)
     */
    Book update(Book book);
    /**
     * BS-BSC-4 (SM_34)
     */
    Book verify(String bookID) throws NotFoundException;
    /**
     * BS-BSC-5 (SM_35)
     */
    List<Book> getAll();
    /**
     * BS-BSC-6 (SM_36)
     */
    List<Book> findByAuthor(String author) throws NotFoundException;
    /**
     * BS-BSC-7 (SM_37)
     */
    List<Book> search(String title,String genre,boolean rateSort,boolean commentSort) throws NotFoundException;
    /**
     * BS-BSC-8 (SM_38)
     */
    void delete(String bookId) throws NotFoundException;
    /**
     * BS-BSC-9 (SM_39)
     */
    List<Book> findBookByVerifiedIsFalse() throws NotFoundException;

}
