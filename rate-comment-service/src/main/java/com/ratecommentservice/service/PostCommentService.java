package com.ratecommentservice.service;

import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.PostComment;

import java.util.List;

public interface PostCommentService {
    PostComment commentPost(PostComment comment) throws PostCommentNotFound;
    List<PostComment> getCommentsByPostID(Long postID) throws PostCommentNotFound;
}
