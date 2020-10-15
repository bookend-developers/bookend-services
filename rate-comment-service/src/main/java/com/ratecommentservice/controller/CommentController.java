package com.ratecommentservice.controller;

import com.ratecommentservice.model.Comment;
import com.ratecommentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }
    @GetMapping("/{bookid}")
    public List<Comment> getBookComments(@PathVariable("bookid") String bookId){
        return commentService.getBookComments(bookId);
    }
    @GetMapping("/user")
    public List<Comment> getUserComments(OAuth2Authentication auth){
        return commentService.getUserComments(auth.getName());
    }
    @PostMapping("/new/{bookid}")
    public Comment commentBook(@RequestParam("comment") String comment
            ,OAuth2Authentication auth
            ,@PathVariable("bookid") String bookId){
        return commentService.commentBook(new Comment(bookId,auth.getName(),comment));
    }
    @DeleteMapping("/delete/{commentid}")
    public void deleteComment(@PathVariable("commentid") String commentId){
        commentService.deleteComment(Long.valueOf(commentId));
    }
}
