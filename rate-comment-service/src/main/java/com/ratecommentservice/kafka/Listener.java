package com.ratecommentservice.kafka;


import com.ratecommentservice.service.CommentService;
import com.ratecommentservice.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    private RateService rateService;
    private CommentService commentService;
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


}
