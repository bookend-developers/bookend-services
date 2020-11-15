package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.RateRequest;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RateServiceImpl implements RateService {
    private RateRepository rateRepository;
    private BookRepository bookRepository;
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
    public Double getBookAverageRate(String bookID) {
        Book book = bookRepository.findBookByBookId(bookID);

        return book.getAverageRate();
    }

    @Override
    public Rate save(RateRequest rateRequest, String username) {
        Rate rate;
        Book book = bookRepository.findBookByBookId(rateRequest.getBookId());
        if(book==null){
            book = new Book(rateRequest.getBookId(),rateRequest.getBookname());

        }
        rate = rateRepository.findByBookAndUsername(book,username);


        if(rate == null){
            rate = new Rate(book,username,rateRequest.getRate());
        }else{
            rate.setRate(rateRequest.getRate());
        }

        book.getRates().add(rate);
        book.setAverageRate(book.calAv());
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
        rates.forEach(rate -> rateRepository.delete(rate));
    }

    @Override
    public Rate findByRateID(Long rateId) {
        return rateRepository.findByRateId(rateId);
    }

    @Override
    public Rate findRateByBookIdandUsername(String bookId, String username) {
        Book book = bookRepository.findBookByBookId(bookId);
        return rateRepository.findByBookAndUsername(book,username);
    }
}
