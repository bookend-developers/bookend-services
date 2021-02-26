package com.ratecommentservice.serviceTest;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.CommentNotFound;
import com.ratecommentservice.kafka.Producer;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.payload.CommentRequest;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.CommentRepository;
import com.ratecommentservice.service.BookServiceImpl;
import com.ratecommentservice.service.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private BookServiceImpl bookService;
    @Mock
    private Producer producer;
    @Test
    void shouldGetUserCommentsWithGivenUsername(){
        List<Comment> comments = new ArrayList<>();
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Comment comment = new Comment(book, "huri", "Very Good");
        comments.add(comment);
        given(commentRepository.findCommentByUsername("huri")).willReturn(comments);
        final List<Comment> expected = commentService.getUserComments("huri");
        assertEquals(comments,expected);
    }

    @Test
    void shouldGetBookCommentsIfGivenBookIdMatchesSuccessfully() throws BookNotFound {
        List<Comment> comments = new ArrayList<>();
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Comment comment = new Comment(book, "huri", "Very Good");
        comments.add(comment);
        given(bookRepository.findBookByBookId("5")).willReturn(book);
        given(commentRepository.findByBook(book)).willReturn(comments);
        final List<Comment> expected = commentService.getBookComments("5");
        assertEquals(comments,expected);
    }

    @Test
    void failToGetBookCommentsIfBookIdDoesNotMatch() throws BookNotFound {
        final String id = "5";
        given(bookRepository.findBookByBookId(id)).willReturn(null);
        assertThrows(BookNotFound.class,()->{
            commentService.getBookComments(id);
        });
    }

    @Test
    void getCommentIfCommentIdHaveMatchSuccesfully() throws CommentNotFound {
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Comment comment = new Comment(Long.valueOf("6"),book, "huri", "Very Good");
        given(commentRepository.findByCommentId(Long.valueOf("6"))).willReturn(comment);
        final Comment expected = commentService.findCommentId(Long.valueOf("6"));
        assertThat(expected).isNotNull();
        assertEquals(comment,expected);
    }

    @Test
    void failToGetShelfsBooksIfIdDoesNotMatch() throws CommentNotFound {
        final Long id = Long.valueOf(5);
        given(commentRepository.findByCommentId(id)).willReturn(null);
        assertThrows(CommentNotFound.class,()->{
            commentService.findCommentId(id);
        });
    }

    @Test
    void shouldDeleteCommentWithGivenComment()  {
        final Long id = Long.valueOf(7);
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Comment comment = new Comment(id,book, "huri", "Very Good");
        //given(commentRepository.delete(comment)).willReturn(); (voidd)
        commentService.deleteComment(comment);
        verify(commentRepository,times(1)).delete(any(Comment.class));
    }

    @Test
    void shouldDeleteBookCommentsWithGivenBookId()  {
        List<Comment> comments = new ArrayList<>();
        final Long id = Long.valueOf(7);
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Comment comment = new Comment(id,book, "huri", "Very Good");
        comments.add(comment);
        given(bookRepository.findBookByBookId("5")).willReturn(book);
        given(commentRepository.findByBook(book)).willReturn(comments);
        //comments.forEach(comm -> commentRepository.delete(comm));
        commentService.deleteCommentByBookId("5");
        verify(commentRepository,times(1)).delete(any(Comment.class));
    }

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldSaveGivenBookAndCommentSuccessfully() throws BookNotFound {

        final String id = "5dgf4dfg";
        final String username = "huri";
        final CommentRequest commreq= new CommentRequest("Very Good", id, "Yuzbasının Kızı");
        final Book book = new Book(commreq.getBookID(),commreq.getBookname());

        final Comment comment = new Comment(Long.valueOf(4564),book,username,commreq.getComment());
        given(commentRepository.save(any(Comment.class))).willReturn(comment);
        given(bookService.findBookByBookID(commreq.getBookID())).willReturn(book);
        book.getComments().add(comment);
        final Comment commentSaved = commentService.commentBook(commreq,  username);
        assertThat(commentSaved).isNotNull();
        verify(commentRepository).save(any(Comment.class));
    }

}
