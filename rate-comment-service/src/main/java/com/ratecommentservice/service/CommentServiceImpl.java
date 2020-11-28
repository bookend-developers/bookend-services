package com.ratecommentservice.service;

import com.ratecommentservice.kafka.Producer;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.payload.CommentRequest;
import com.ratecommentservice.payload.KafkaMessage;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String COMMENT_TOPIC = "new-comment";
    private CommentRepository commentRepository;
    private BookRepository bookRepository;
    private BookService bookService;
    private Producer producer;
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }
    @Autowired
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }



    @Override
    public List<Comment> getUserComments(String username) {
        return commentRepository.findCommentByUsername(username);
    }

    @Override
    public List<Comment> getBookComments(String bookId) {
        Book book = bookRepository.findBookByBookId(bookId);
        return commentRepository.findByBook(book);
    }

    @Override
    public Comment commentBook(CommentRequest commentRequest, String username) {
        Comment comment = new Comment();
        Book book = bookService.findBookByBookID(commentRequest.getBookID());
        if(book == null){
            book = bookService.save(new Book(commentRequest.getBookID(),commentRequest.getBookname()));
        }
        comment.setBook(book);
        comment.setComment(commentRequest.getComment());
        comment.setUsername(username);

        comment = commentRepository.save(comment);
        book.getComments().add(comment);
        bookRepository.save(book);

        Map<String, String> message= new HashMap<String, String>();
        message.put("book",book.getBookid());
        message.put("comment",comment.getCommentId().toString());
        KafkaMessage kafkaMessage = new KafkaMessage(COMMENT_TOPIC,message);
        producer.publishNewRate(kafkaMessage);
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public void deleteCommentByBookId(String bookId)
    {
        Book book = bookRepository.findBookByBookId(bookId);
        List<Comment> comments = commentRepository.findByBook(book);
        comments.forEach(comment -> commentRepository.delete(comment));
    }

    @Override
    public Comment findCommentId(Long commentId) {
        return commentRepository.findByCommentId(commentId);
    }
}
