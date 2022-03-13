package com.bookend.bookservice.service;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.payload.BookRequest;

import java.util.List;

public interface BookService {
    Book getById(String id) throws NotFoundException;
    Book save(BookRequest bookRequest) throws MandatoryFieldException, AlreadyExist;
    Book update(Book book);
    List<Book> getAll();
    List<Book> findByAuthor(String author) throws NotFoundException;
    List<Book> search(String title,String genre,boolean rateSort,boolean commentSort) throws NotFoundException;
    void delete(String bookId) throws NotFoundException;
    List<Book> findBookByVerifiedIsFalse() throws NotFoundException;

}
