package com.ratecommentservice.payload;

import java.time.LocalDateTime;

public class PostCommentRequest {
    private String comment;
    private Long postID;
    private String username;
    private LocalDateTime localDateTime;

    public PostCommentRequest(String comment, Long postID, String username) {
        this.comment = comment;
        this.postID = postID;
        this.username = username;
        this.localDateTime = LocalDateTime.now();
    }

    public PostCommentRequest() {
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
