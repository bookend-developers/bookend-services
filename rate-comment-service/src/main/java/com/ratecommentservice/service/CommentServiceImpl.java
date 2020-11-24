package com.ratecommentservice.service;

import com.ratecommentservice.kafka.Producer;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
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
    private Producer producer;
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
    public Comment commentBook(Comment comment) {
        Book book = comment.getBook();
        book.getComments().add(comment);
        bookRepository.save(book);
        comment = commentRepository.save(comment);
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
