package com.bookclupservice.bookclubservice.payload.request;

import java.time.LocalDateTime;

public class CommentRequest {
    private String comment;
    private Long postID;
    private String username;
    private LocalDateTime localDateTime;

    public CommentRequest(String comment, Long postID, String username, LocalDateTime localDateTime) {
        this.comment = comment;
        this.postID = postID;
        this.username = username;
        this.localDateTime = localDateTime;
    }

    public CommentRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
