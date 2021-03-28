package com.ratecommentservice.service;

import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.repository.PostCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class PostCommentServiceImpl implements PostCommentService {
    private PostCommentRepository commentRepository;
    @Autowired
    public void setCommentRepository(PostCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public PostComment commentPost(PostComment comment) throws PostCommentNotFound {
        PostComment postComment =  commentRepository.save(comment);
        if(postComment == null){
            throw new PostCommentNotFound("Post Comments are not found..");
        }
        return postComment;
    }

    @Override
    public List<PostComment> getCommentsByPostID(Long postID) throws PostCommentNotFound {
        List<PostComment> comments = commentRepository.findAllByPostIDOrderByDateAsc(postID);
        if(comments.isEmpty() ){
            throw new PostCommentNotFound("Post Comments are not found..");
        }
        Collections.reverse(comments);
        return comments;
    }
}
