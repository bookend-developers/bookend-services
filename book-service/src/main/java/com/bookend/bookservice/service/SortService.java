package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.SortedLists;
/**
 * BS-SSC stands for BookService-SortServiceClass
 * SM stands for ServiceMethod
 */
public interface SortService {
    /**
     * BS-SSC-1 (SM_45)
     */
    SortedLists add(Book book);

    /**
     * BS-SSC-2 (SM_46)
     */
    SortedLists sort(String type);
    /**
     * BS-SSC-3 (SM_47)
     */
    SortedLists findOne();
    /**
     * BS-SSC-4 (SM_48)
     */
    SortedLists remove(Book book);
}
