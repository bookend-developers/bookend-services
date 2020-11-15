package com.ratecommentservice.service;

import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    private PostCommentRepository commentRepository;
    @Autowired
    public void setCommentRepository(PostCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public PostComment commentPost(PostComment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<PostComment> getCommentsByPostID(Long postID) {
        return commentRepository.findAllByPostIDOrderByDateAsc(postID);
    }
}
