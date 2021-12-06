package com.bookend.bookclubservice.repository;

import com.bookend.bookclubservice.model.Member;
import com.bookend.bookclubservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findByWriter(Member writer);
    List<Post> findByClubId(Long clubId);
    Post findPostById(Long postId);

}
