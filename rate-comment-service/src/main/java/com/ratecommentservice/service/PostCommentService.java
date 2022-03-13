package com.ratecommentservice.service;

import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.PostComment;

import java.util.List;
/**
 * RCS-PCSC stands for RateCommentService-PostCommentServiceClass
 * SM stands for ServiceMethod
 */
public interface PostCommentService {
    /**
     * RCS-PCSC-1 (SM_69)
     */
    PostComment commentPost(PostComment comment) throws PostCommentNotFound;
    /**
     * RCS-PCSC-2 (SM_70)
     */
    List<PostComment> getCommentsByPostID(Long postID) throws PostCommentNotFound;
}
