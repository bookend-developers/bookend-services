package com.ratecommentservice.service;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
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
        List<Double> rates = rateRepository.findByBook(book).stream()
                .map(p -> p.getRate())
                .collect(Collectors.toList());
        if(rates==null){
            return 0.0;
        }
        return rates.stream().mapToDouble(a -> a)
                .average().getAsDouble();
    }

    @Override
    public Rate save(Rate newRate) {
        return rateRepository.save(newRate);
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
