package com.ratecommentservice.service;

import com.ratecommentservice.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getUserComments(String username);
    List<Comment> getBookComments(String bookId);
    Comment commentBook(Comment comment);
    void deleteComment(Long commentId);
    void deleteCommentByBookId(String bookId);
}
