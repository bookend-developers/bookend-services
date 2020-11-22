package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private BookRepository bookRepository;
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Override
    public List<Comment> getUserComments(String username) {
        return commentRepository.findCommentByUsername(username);
    }

    @Override
    public List<Comment> getBookComments(String bookId) {
        Book book = bookRepository.findBookByBookId(bookId);
        return commentRepository.findByBook(book);
    }

    @Override
    public Comment commentBook(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteCommentByBookId(String bookId)
    {
        Book book = bookRepository.findBookByBookId(bookId);
        List<Comment> comments = commentRepository.findByBook(book);
        comments.forEach(comment -> commentRepository.delete(comment));
    }

    @Override
    public Comment findCommentId(Long commentId) {
        return commentRepository.findByCommentId(commentId);
    }
}
