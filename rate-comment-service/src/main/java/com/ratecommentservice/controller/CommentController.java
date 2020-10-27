package com.ratecommentservice.controller;

import com.ratecommentservice.model.Comment;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation(value = "Get book's comments ", response = Comment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")
    }
    )
    @GetMapping("/{bookid}")
    public List<Comment> getBookComments(@PathVariable("bookid") String bookId){
        return commentService.getBookComments(bookId);
    }
    @ApiOperation(value = "View user's comments ", response = Comment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved comment list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
    }
    )
    @GetMapping("/user")
    public List<Comment> getUserComments(OAuth2Authentication auth){

        return commentService.getUserComments(auth.getName());
    }
    @ApiOperation(value = "Leave a comment for a specific book", response = Comment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully commenting the book"),
            @ApiResponse(code = 401, message = "You are not authorized to comment the book"),
            @ApiResponse(code = 400, message = "The way you are trying to comment is not accepted.")
    }
    )

    @PostMapping("/new/{bookid}")
    public Comment commentBook(@RequestParam("comment") String comment
            ,OAuth2Authentication auth
            ,@PathVariable("bookid") String bookId){
        return commentService.commentBook(new Comment(bookId,auth.getName(),comment));
    }
    @ApiOperation(value = "Delete a specific comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted comment"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource")
    }
    )
    @DeleteMapping("/delete/{commentid}")
    public void deleteComment(@PathVariable("commentid") String commentId){
        commentService.deleteComment(Long.valueOf(commentId));
    }
}
