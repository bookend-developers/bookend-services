package com.ratecommentservice.service;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.RateNotFound;
import com.ratecommentservice.kafka.Producer;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.KafkaMessage;
import com.ratecommentservice.payload.RateRequest;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    private static final String RATE_TOPIC = "new-rate";
    private RateRepository rateRepository;
    private BookRepository bookRepository;
    private Producer producer;
    @Autowired
    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    @Autowired
    public void setRateRepository(RateRepository rateRepository){this.rateRepository=rateRepository;}
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Rate> getUserRates(String username) {

        return rateRepository.findByUsername(username);
    }

    @Override
    public Double getBookAverageRate(String bookID) throws BookNotFound, RateNotFound {
        Book book = bookRepository.findBookByBookId(bookID);
        if(book == null){
            throw new BookNotFound("Book is not found");
        }
        Double averageRate = book.getAverageRate();
        if(averageRate == null){
            throw new RateNotFound("Rate is not found");
        }
        return averageRate;
    }

    @Override
    public Rate save(RateRequest rateRequest, String username) {
        Rate rate;
        Book book = bookRepository.findBookByBookId(rateRequest.getBookId());
        if(book==null){
            book = new Book(rateRequest.getBookId(),rateRequest.getBookname());
            bookRepository.save(book);

        }
        rate = rateRepository.findByBookAndUsername(book,username);


        if(rate == null){
            rate = rateRepository.save(new Rate(book,username,rateRequest.getRate()));
        }else{
            rate.setRate(rateRequest.getRate());
        }

        book.getRates().add(rate);
        book.setAverageRate(book.calAv());
        Map<String, String> message= new HashMap<String, String>();
        message.put("book",book.getBookid());
        message.put("rate",book.getAverageRate().toString());

        KafkaMessage kafkaMessage = new KafkaMessage(RATE_TOPIC,message);
        producer.publishNewRate(kafkaMessage);
        bookRepository.save(book);

        return rateRepository.save(rate);
    }

    @Override
    public void deleteRate(Rate rate) {

        rateRepository.delete(rate);
    }

    @Override
    public void deleteRateByBookId(String bookId) {
        Book book = bookRepository.findBookByBookId(bookId);
        List<Rate> rates = rateRepository.findByBook(book);
        //rates.forEach(rate -> rateRepository.delete(rate));
    }

    @Override
    public Rate findByRateID(Long rateId) throws RateNotFound {
        Rate rate = rateRepository.findByRateId(rateId);
        if(rate == null){
            throw new RateNotFound("Rate is not found");
        }
        return rate;
    }

    @Override
    public Rate findRateByBookIdandUsername(String bookId, String username) throws BookNotFound, RateNotFound {
        Book book = bookRepository.findBookByBookId(bookId);
        if(book == null){
            throw new BookNotFound("Book is not found");
        }
        Rate rate = rateRepository.findByBookAndUsername(book,username);
        if(rate == null){
            throw new RateNotFound("Rate is not found");
        }
        return rate;
    }

}
