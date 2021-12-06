package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.CommentNotFound;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.payload.CommentRequest;

import java.util.List;
/**
 * RCS-CSC stands for RateCommentService-CommentServiceClass
 * SM stands for ServiceMethod
 */
public interface CommentService {
    /**
     * RCS-CSC-1 (SM_63)
     */
    List<Comment> getUserComments(String username);
    /**
     * RCS-CSC-2 (SM_64)
     */
    List<Comment> getBookComments(String bookId) throws BookNotFound;
    /**
     * RCS-CSC-3 (SM_65)
     */
    Comment commentBook(CommentRequest commentRequest,String username) throws BookNotFound;
    /**
     * RCS-CSC-4 (SM_66)
     */
    void deleteComment(Comment comment);
    /**
     * RCS-CSC-5 (SM_67)
     */
    void deleteCommentByBookId(String bookId);
    /**
     * RCS-CSC-6 (SM_68)
     */
    Comment findCommentId(Long commentId) throws CommentNotFound;
}
