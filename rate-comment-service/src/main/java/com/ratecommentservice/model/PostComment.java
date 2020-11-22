package com.ratecommentservice.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="post_comments")
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;


    private Long postID;
    private String username;
    @Column(name = "comment",columnDefinition = "varchar(5465)")
    private String comment;
    private LocalDateTime date;

    public PostComment(Long postID, String username, String comment) {
        this.postID = postID;
        this.username = username;
        this.comment = comment;
        this.date = LocalDateTime.now();
    }

    public PostComment() {
    }

    public Long getCommentId() {
        return commentId;
    }


    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
