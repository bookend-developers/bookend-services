package com.bookend.bookservice.comparator;

import com.bookend.bookservice.model.Book;

import java.util.Comparator;

public class BookComparator implements Comparator<Book> {
    private final String type;

    public BookComparator(String type) {
        this.type = type;
    }
    public int compare(Book book, Book other){
        if(type.equals("rate")){
            return book.getRate().compareTo(other.getRate());
        }
        return book.getComments().size() > other.getComments().size() ? 1
                : book.getComments().size() < other.getComments().size() ? -1
                : 0 ;
    }
}
