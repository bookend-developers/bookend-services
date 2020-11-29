package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.SortedLists;

public interface SortService {

    void add(Book book);
    void add(Book book,String type);
    SortedLists sort();
    SortedLists sort(String type);
    SortedLists findOne();
    SortedLists remove(Book book,String type);
}
