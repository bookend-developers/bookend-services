package com.ratecommentservice.serviceTest;

import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.repository.PostCommentRepository;
import com.ratecommentservice.service.PostCommentService;
import com.ratecommentservice.service.PostCommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostCommentServiceTest {

    @Mock
    private PostCommentRepository postCommentRepository;
    @InjectMocks
    private PostCommentServiceImpl postCommentService;


    @Test
    void getPostCommentIfPostIdHaveMatchSuccesfully() throws PostCommentNotFound {
        List<PostComment> comments = new ArrayList<>();
        String id = "5";
        final PostComment postComment = new PostComment(Long.valueOf(id),"huri","Amazing");
        comments.add(postComment);
        given(postCommentRepository.findAllByPostIDOrderByDateAsc(Long.valueOf(id))).willReturn(comments);
        final List<PostComment> expected = postCommentService.getCommentsByPostID(Long.valueOf(id));
        assertThat(expected).isNotNull();
        assertEquals(comments,expected);
    }

    @Test
    void failToGetPostCommentIfPostIdDoesNotMatch() throws PostCommentNotFound {
        List<PostComment> comments = new ArrayList<>();
        given(postCommentRepository.findAllByPostIDOrderByDateAsc(Long.valueOf("5"))).willReturn(comments);
        assertThrows(PostCommentNotFound.class,()->{
            postCommentService.getCommentsByPostID(Long.valueOf("5"));
        });
    }

    @Test
    void shouldSaveGivenPostCommentSuccessfully() throws PostCommentNotFound {
        String id = "5";
        final PostComment postComment = new PostComment(Long.valueOf(id),"huri","Amazing");
        given(postCommentRepository.save(any(PostComment.class))).willReturn(postComment);
        final PostComment saved = postCommentService.commentPost(postComment);
        assertThat(saved).isNotNull();
        verify(postCommentRepository).save(any(PostComment.class));
    }

    @Test
    void failToSavePostCommentIfCommentIsNull() throws PostCommentNotFound {
        assertThrows(PostCommentNotFound.class,()->{
            postCommentService.commentPost(null);
        });
        verify(postCommentRepository, never()).save(any(PostComment.class));
    }

}
