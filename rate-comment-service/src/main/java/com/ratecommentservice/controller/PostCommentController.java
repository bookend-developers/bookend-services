package com.ratecommentservice.controller;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.payload.CommentRequest;
import com.ratecommentservice.payload.PostCommentRequest;
import com.ratecommentservice.service.CommentServiceImpl;
import com.ratecommentservice.service.PostCommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/post/comment")
public class PostCommentController {
    private PostCommentService commentService;
    @Autowired
    public void setCommentService(PostCommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Leave a comment for a specific post", response = PostComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully commenting the post"),
            @ApiResponse(code = 401, message = "You are not authorized to comment the post"),
            @ApiResponse(code = 400, message = "The way you are trying to comment is not accepted.")
    }
    )

    @PostMapping("/new")
    public PostComment commentPost(@RequestBody PostCommentRequest commentRequest
            , OAuth2Authentication auth){
        if(commentRequest.getComment()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to comment is not accepted.");
        }
        if(commentRequest.getPostID()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to comment is not accepted.");
        }


        return commentService.commentPost(new PostComment(commentRequest.getPostID(),auth.getName(),commentRequest.getComment(), LocalDateTime.now()));
    }
    @ApiOperation(value = "Get post's comments ", response = PostComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{postid}")
    public List<PostComment> getBookComments(@PathVariable("postid") Long postID){
        return commentService.getCommentsByPostID(postID);
    }
}
