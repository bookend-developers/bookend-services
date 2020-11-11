package com.ratecommentservice.repository;

import com.ratecommentservice.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBookId(String bookId);
    List<Comment> findByUsername(String username);
    void deleteByBookId(String bookId);
}