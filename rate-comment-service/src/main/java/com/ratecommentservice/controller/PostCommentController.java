package com.ratecommentservice.controller;


import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.model.PostComment;

import com.ratecommentservice.service.PostCommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * RCS-PCC stands for RateCommentService-PostCommentController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/post/comment")
public class PostCommentController {
    private PostCommentService commentService;
    @Autowired
    public void setCommentService(PostCommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * RCS-PCC-1 (CM_47)
     */
    @ApiOperation(value = "Get post's comments ", response = PostComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{postid}")
    public List<PostComment> getPostComments(@PathVariable("postid") Long postID) throws PostCommentNotFound {
        return commentService.getCommentsByPostID(postID);
    }
}
