package com.ratecommentservice.controller;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.CommentRequest;
import com.ratecommentservice.payload.MessageResponse;
import com.ratecommentservice.service.BookService;
import com.ratecommentservice.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private CommentService commentService;
    private BookService bookService;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

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

    @PostMapping("/new")
    public Comment commentBook(@RequestBody CommentRequest commentRequest
            ,OAuth2Authentication auth){
        if(commentRequest.getBookID()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to comment is not accepted.");
        }
        if(commentRequest.getComment()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to comment is not accepted.");
        }
        if(commentRequest.getBookname()==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The way you are trying to comment is not accepted.");
        }
        Book book = bookService.findBookByBookID(commentRequest.getBookID());
        if(book==null){
            Book newBook = new Book(commentRequest.getBookID(),commentRequest.getBookname());

            bookService.save(newBook);
            return commentService.commentBook(new Comment(newBook,auth.getName(),commentRequest.getComment()));
        }

        return commentService.commentBook(new Comment(book,auth.getName(),commentRequest.getComment()));
    }
    @ApiOperation(value = "Delete a specific comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted comment"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the resource"),
            @ApiResponse(code = 403, message = "The operation is forbidden"),
    }
    )
    @DeleteMapping("/delete/{commentid}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentid") String commentId, OAuth2Authentication auth){
        Comment comment = commentService.findCommentId(Long.valueOf(commentId));
        if(comment==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found.");
        }
        if(!comment.getUsername().equals(auth.getName())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"The operation is forbidden.");
        }
        commentService.deleteComment(comment);
        return   ResponseEntity.ok(new MessageResponse("Comment deleted successfully."));
    }
    @GetMapping("/book/{bookid}")
    public Book getBook(@PathVariable("bookid") String bookId){
        Book book = bookService.findBookByBookID(bookId);
        if(book== null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource is not found");
        }
        return bookService.findBookByBookID(bookId);
    }
}
