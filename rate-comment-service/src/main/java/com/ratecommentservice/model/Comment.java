package com.ratecommentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;
    private String username;
    @Column(name = "comment",columnDefinition = "varchar(5465)")
    private String comment;
    private LocalDateTime date;

    public Comment(Book book, String username, String comment) {
        this.book = book;
        this.username = username;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public Comment(Long commentId, Book book, String username, String comment) {
        this.commentId = commentId;
        this.book = book;
        this.username = username;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public Comment() {
        this.date = LocalDateTime.now();
    }

    public Long getCommentId() {
        return commentId;
    }



    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
