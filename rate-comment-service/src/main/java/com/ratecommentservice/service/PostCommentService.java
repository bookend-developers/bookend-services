package com.ratecommentservice.service;

import com.ratecommentservice.model.PostComment;

import java.util.List;

public interface PostCommentService {
    PostComment commentPost(PostComment comment);
    List<PostComment> getCommentsByPostID(Long postID);
}
