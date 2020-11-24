package com.bookend.bookservice.model;

import com.bookend.bookservice.sort.SortedListByRate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookListByRate {
    @Id
    private String id;
    private SortedListByRate sortedListByRate;

    public BookListByRate(SortedListByRate sortedListByRate) {
        this.sortedListByRate = sortedListByRate;
    }

    public BookListByRate() {
    }

    public String getId() {
        return id;
    }


    public SortedListByRate getSortedListByRate() {
        return sortedListByRate;
    }

    public void setSortedListByRate(SortedListByRate sortedListByRate) {
        this.sortedListByRate = sortedListByRate;
    }
}
