package com.ratecommentservice.service;

import com.ratecommentservice.model.Comment;
import com.ratecommentservice.payload.CommentRequest;

import java.util.List;

public interface CommentService {
    List<Comment> getUserComments(String username);
    List<Comment> getBookComments(String bookId);
    Comment commentBook(CommentRequest commentRequest,String username);
    void deleteComment(Comment comment);
    void deleteCommentByBookId(String bookId);
    Comment findCommentId(Long commentId);
}
