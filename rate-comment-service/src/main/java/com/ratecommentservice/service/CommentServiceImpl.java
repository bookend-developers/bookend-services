package com.ratecommentservice.service;

import com.ratecommentservice.model.Comment;
import com.ratecommentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getUserComments(String username) {
        return commentRepository.findByUsername(username);
    }

    @Override
    public List<Comment> getBookComments(String bookId) {

        return commentRepository.findByBookId(bookId);
    }

    @Override
    public Comment commentBook(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
         commentRepository.deleteById(commentId);
    }
}
