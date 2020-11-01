package com.ratecommentservice.payload;

public class CommentRequest {
    private String comment;
    private String bookID;
    private String bookname;

    public CommentRequest(String comment, String bookID, String bookname) {
        this.comment = comment;
        this.bookID = bookID;
        this.bookname = bookname;
    }

    public CommentRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
