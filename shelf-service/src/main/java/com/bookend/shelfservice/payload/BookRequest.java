package com.bookend.shelfservice.payload;

public class BookRequest {
    private String bookid;
    private String bookName;

    public BookRequest(String bookid, String bookName) {
        this.bookid = bookid;
        this.bookName = bookName;
    }

    public BookRequest() {
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
