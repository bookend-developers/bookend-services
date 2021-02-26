package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.CommentNotFound;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.payload.CommentRequest;

import java.util.List;

public interface CommentService {
    List<Comment> getUserComments(String username);
    List<Comment> getBookComments(String bookId) throws BookNotFound;
    Comment commentBook(CommentRequest commentRequest,String username) throws BookNotFound;
    void deleteComment(Comment comment);
    void deleteCommentByBookId(String bookId);
    Comment findCommentId(Long commentId) throws CommentNotFound;
}
