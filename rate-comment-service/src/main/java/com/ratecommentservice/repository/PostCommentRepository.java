package com.ratecommentservice.repository;

import com.ratecommentservice.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostCommentRepository extends JpaRepository<PostComment,Long> {
    List<PostComment> findAllByPostID(Long postId);
    List<PostComment> findAllByPostIDOrderByDateAsc(Long postID);
}
