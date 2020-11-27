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
        if(!isExists(book,sortedByComment)){
            sortedByComment.add(book);
        }
        if(!isExists(book,sortedByRate)){
            sortedByRate.add(book);
        }
        return sort();

    }
    public SortedLists add(Book book, String type){
        if(type.equals("rate")){
           sortedByRate.add(book);
        }
        if (type.equals("comment")){
            sortedByComment.add(book);
        }
        return sort();
    }
    private boolean isExists(Book book, List<Book> books){
        return books.contains(book);
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
    public SortedLists remove(Book book, String type){
        if(type.equals("rate")){
            Iterator<Book> rateIterator = sortedByRate.iterator();
             while(rateIterator.hasNext()){
                 Book b = rateIterator.next();
                 if(b.getId().equals(book.getId())){
                     rateIterator.remove();
                 }
             }
        }
        if (type.equals("comment")){
            Iterator<Book> commentIterator = sortedByComment.iterator();
            while(commentIterator.hasNext()){
                Book b = commentIterator.next();
                if(b.getId().equals(book.getId())){
                    commentIterator.remove();
                }
            }
        }
        return this;
    }


}
