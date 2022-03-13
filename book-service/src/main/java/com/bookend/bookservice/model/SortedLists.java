package com.bookend.bookservice.model;


import com.bookend.bookservice.comparator.BookComparator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Document(collection = "sortedList")
public class SortedLists {
    @Id
    private String id;
    private List<Book> sortedByRate;
    private List<Book> sortedByComment;
    private String name;




    public SortedLists(List<Book> sortedByRate, List<Book> sortedByComment,String name) {
        this.sortedByRate = sortedByRate;
        this.sortedByComment = sortedByComment;
        this.name = name;
    }

    public SortedLists(String name) {
        this.name = name;
        this.sortedByRate = new ArrayList<>();
        this.sortedByComment = new ArrayList<>();
    }

    public SortedLists() {
        this.sortedByRate = new ArrayList<>();
        this.sortedByComment = new ArrayList<>();

    }

    public List<Book> getSortedByRate() {
        return sortedByRate;
    }

    public void setSortedByRate(List<Book> sortedByRate) {
        this.sortedByRate = sortedByRate;
    }

    public List<Book> getSortedByComment() {
        return sortedByComment;
    }

    public void setSortedByComment(List<Book> sortedByComment) {
        this.sortedByComment = sortedByComment;
    }

    public String getId() {
        return id;
    }
    public SortedLists add(Book book){
        sortedByComment.add(book);
        sortedByRate.add(book);
        return sort();

    }

    public SortedLists sort(){

        Collections.sort(sortedByComment, new BookComparator("comment"));
        Collections.sort(sortedByRate, new BookComparator("rate"));
        return this;
    }
    public SortedLists sortByType(String type){
        if(type.equals("rate")){
            Collections.sort(sortedByRate, new BookComparator(type));
        }
        if (type.equals("comment")){
            Collections.sort(sortedByComment, new BookComparator(type));
        }
        return this;

    }
    public SortedLists remove(Book book){
        Iterator<Book> rateIterator = sortedByRate.iterator();
         while(rateIterator.hasNext()){
             Book b = rateIterator.next();
             if(b.getId().equals(book.getId())){
                 rateIterator.remove();
             }
         }
        Iterator<Book> commentIterator = sortedByComment.iterator();
        while(commentIterator.hasNext()){
            Book b = commentIterator.next();
            if(b.getId().equals(book.getId())){
                commentIterator.remove();
            }
        }

        return this;
    }


}
