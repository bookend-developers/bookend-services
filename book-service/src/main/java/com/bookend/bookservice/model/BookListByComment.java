package com.bookend.bookservice.model;

import com.bookend.bookservice.sort.SortedListByComment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookListByComment {
    @Id
    private String id;
    private SortedListByComment sortedListByComment;

    public BookListByComment(SortedListByComment sortedListByComment) {
        this.sortedListByComment = sortedListByComment;
    }

    public BookListByComment() {
    }

    public String getId() {
        return id;
    }

    public SortedListByComment getSortedListByComment() {
        return sortedListByComment;
    }

    public void setSortedListByComment(SortedListByComment sortedListByComment) {
        this.sortedListByComment = sortedListByComment;
    }
}
