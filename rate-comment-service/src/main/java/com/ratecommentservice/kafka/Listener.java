package com.ratecommentservice.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.payload.PostCommentRequest;
import com.ratecommentservice.service.CommentService;
import com.ratecommentservice.service.PostCommentService;
import com.ratecommentservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Listener {

    private RateService rateService;
    private CommentService commentService;
    private PostCommentService postCommentService;
    @Autowired
    public void setPostCommentService(PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
    }

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }

    @KafkaListener(topics = "deleting-book",
            groupId ="bookend-rate-commentservice")
    public void consumeBook(String message) {
        System.out.println(message);
        String[] splited = message.split("\"");
        rateService.deleteRateByBookId(splited[1]);
        commentService.deleteCommentByBookId(splited[1]);


    }
    @KafkaListener(topics = "comment",
            groupId ="bookend-rate-commentservice")
    public void consumeComment(String message) {
        System.out.println(message);
        String[] splited = message.split("\"");
        ObjectMapper mapper = new ObjectMapper();
        try {
            PostCommentRequest commentRequest = mapper.readValue(message, PostCommentRequest.class);
            PostComment postComment  = new PostComment(commentRequest.getPostID()
                                                    ,commentRequest.getUsername()
                                                    ,commentRequest.getComment());
            postCommentService.commentPost(postComment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (PostCommentNotFound postCommentNotFound) {
            postCommentNotFound.printStackTrace();
        }


    }


}
