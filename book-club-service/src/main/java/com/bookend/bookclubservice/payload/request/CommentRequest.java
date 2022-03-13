package com.bookend.bookclubservice.payload.request;

import java.time.LocalDateTime;

public class CommentRequest {
    private String comment;
    private Long postID;
    private String username;

    public CommentRequest(String comment, Long postID, String username) {
        this.comment = comment;
        this.postID = postID;
        this.username = username;
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

}
