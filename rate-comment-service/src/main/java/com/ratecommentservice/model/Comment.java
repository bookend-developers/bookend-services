package com.ratecommentservice.model;

import javax.persistence.*;

@Entity
@Table(name ="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private String bookId;
    private String username;
    @Column(name = "comment",columnDefinition = "varchar(1000)")
    private String comment;

    public Comment(String bookId, String username, String comment) {
        this.bookId = bookId;
        this.username = username;
        this.comment = comment;
    }

    public Comment() {
    }

    public Long getCommentId() {
        return commentId;
    }



    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
