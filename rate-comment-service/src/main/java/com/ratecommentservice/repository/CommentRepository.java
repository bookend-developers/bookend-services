package com.ratecommentservice.repository;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBook(Book bookId);
    List<Comment> findCommentByUsername(String username);
    Comment findByCommentId(Long commentId);
}
