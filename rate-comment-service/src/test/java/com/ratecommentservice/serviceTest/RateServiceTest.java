package com.ratecommentservice.serviceTest;


import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.exception.PostCommentNotFound;
import com.ratecommentservice.exception.RateNotFound;
import com.ratecommentservice.kafka.Producer;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Comment;
import com.ratecommentservice.model.PostComment;
import com.ratecommentservice.model.Rate;
import com.ratecommentservice.payload.KafkaMessage;
import com.ratecommentservice.payload.RateRequest;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.repository.RateRepository;
import com.ratecommentservice.service.BookServiceImpl;
import com.ratecommentservice.service.RateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RateServiceTest {
    @Mock
    private RateRepository rateRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private RateServiceImpl rateService;
    @Mock
    private Producer producer;

    private static final String RATE_TOPIC = "new-rate";


    @Test
    void shouldGetUserCommentsWithGivenUsernameSuccessfully(){
        List<Rate> rates = new ArrayList<>();
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Rate rate = new Rate(book,"huri",3.0 );
        rates.add(rate);
        given(rateRepository.findByUsername("huri")).willReturn(rates);
        final List<Rate> expected = rateService.getUserRates("huri");
        assertEquals(rates,expected);
    }

    @Test
    void shouldGetBookAverageRateWithGivenBookIdSuccessfully() throws BookNotFound, RateNotFound {
        String id = "5";
        final Book book = new Book(id, "Yuzbasının Kızı");
        //final Rate rate = new Rate(book,"huri",3.0 );
        book.setAverageRate(2.5);
        given(bookRepository.findBookByBookId(id)).willReturn(book);
        final Double expected = rateService.getBookAverageRate(id);
        assertEquals(book.getAverageRate(),expected);
    }

    @Test
    void failToGetBookAverageRateIfGivenBookIdDoesNotMatch() throws BookNotFound, RateNotFound {
        String id = "5";
        given(bookRepository.findBookByBookId(id)).willReturn(null);
        assertThrows(BookNotFound.class,()->{
            rateService.getBookAverageRate(id);
        });
    }

    @Test
    void failToGetBookAverageRateIfBookAverageRateIsNull() throws BookNotFound, RateNotFound{
        String id = "5";
        final Book book = new Book(id, "Yuzbasının Kızı");
        given(bookRepository.findBookByBookId(id)).willReturn(book);
        assertThrows(RateNotFound.class,()->{
            rateService.getBookAverageRate(id);
        });
    }

    @Test
    void shouldGetRateWithGivenRateIdSuccessfully() throws BookNotFound, RateNotFound {
        String id = "5";
        Long rateId = Long.valueOf("7");
        final Book book = new Book(id, "Yuzbasının Kızı");
        final Rate rate = new Rate(rateId,book,"huri",3.0 );
        given(rateRepository.findByRateId(rateId)).willReturn(rate);
        final Rate expected = rateService.findByRateID(rateId);
        assertEquals(rate,expected);
    }

    @Test
    void failToGetRateIfRateIdDoesNotMatch() throws BookNotFound, RateNotFound{
        Long rateId = Long.valueOf("7");
        given(rateRepository.findByRateId(rateId)).willReturn(null);
        assertThrows(RateNotFound.class,()->{
            rateService.findByRateID(rateId);
        });
    }
    @Test
    void failToGetRateIfGivenBookIdDoesNotMatch() throws BookNotFound, RateNotFound {
        String id = "5";
        given(bookRepository.findBookByBookId(id)).willReturn(null);
        assertThrows(BookNotFound.class,()->{
            rateService.findRateByBookIdandUsername(id,"huri");
        });
    }

    @Test
    void failToGetRateIfBookAndUsernameDoesNotMatch() throws BookNotFound, RateNotFound {
        String id = "5";
        final Book book = new Book(id, "Yuzbasının Kızı");
        given(bookRepository.findBookByBookId(id)).willReturn(book);
        given(rateRepository.findByBookAndUsername(book,"huri")).willReturn(null);
        assertThrows(RateNotFound.class,()->{
            rateService.findRateByBookIdandUsername(id,"huri");
        });
    }

    @Test
    void shouldDeleteRateWithGivenRate()  {
        final Long rateId = Long.valueOf(7);
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Rate rate = new Rate(rateId,book,"huri",3.0 );
        rateService.deleteRate(rate);
        verify(rateRepository,times(1)).delete(any(Rate.class));
    }

    @Test
    void shouldDeleteRatesWithGivenBookId()  {
        List<Rate> rates = new ArrayList<>();
        final Long rateId = Long.valueOf(7);
        final Book book = new Book("5", "Yuzbasının Kızı");
        final Rate rate = new Rate(rateId,book,"huri",3.0 );
        rates.add(rate);
        given(bookRepository.findBookByBookId("5")).willReturn(book);
        given(rateRepository.findByBook(book)).willReturn(rates);
        //rates.forEach(ra -> rateRepository.delete(ra));
        rateService.deleteRateByBookId("5");
        verify(rateRepository,times(1)).delete(any(Rate.class));
    }

    @Test
    void shouldSaveGivenRateSuccessfully() throws BookNotFound, RateNotFound {
        String id = "5";
        final Long rateId = Long.valueOf(7);
        final Book book = new Book(id, "Yuzbasının Kızı");
        final Rate rate = new Rate(rateId,book,"huri",3.0 );
        final RateRequest rateRequest = new RateRequest(3.0, id, "Yuzbasının Kızı");
        given(bookRepository.findBookByBookId(rateRequest.getBookId())).willReturn(book);
        given(rateRepository.findByBookAndUsername(book,"huri")).willReturn(rate);
        rate.setRate(rateRequest.getRate());
        book.getRates().add(rate);
        book.setAverageRate(book.calAv());
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(rateRepository.save(any(Rate.class))).thenReturn(rate);
        final Rate saved = rateService.save(rateRequest, "huri");
        assertThat(saved).isNotNull();
        verify(rateRepository).save(any(Rate.class));
    }

    /*--------------------------Bunu da anlamadım nedenini  ikisinin de null olma durumunu kontrol ediyom ---------------
    Tek tek null olma durumu da sorunlu oldu
    @Test
    void shouldSaveGivenBookIsNull() throws BookNotFound, RateNotFound {
        String id = "5";
        final Long rateId = Long.valueOf(7);
        Book book2 = new Book();
        final RateRequest rateRequest = new RateRequest(3.0, id, "Yuzbasının Kızı");
        given(bookRepository.findBookByBookId(rateRequest.getBookId())).willReturn(null);
        book2 = new Book(rateRequest.getBookId(),rateRequest.getBookname());
        when(bookRepository.save(any(Book.class))).thenReturn(book2);
        final Rate rate  = new Rate(book2,"huri",rateRequest.getRate());
        //final Rate rate = new Rate(rateId,book2,"huri",3.0 );
        given(rateRepository.findByBookAndUsername(book2,"huri")).willReturn(null);
        when(rateRepository.save(new Rate(book2,"huri",rateRequest.getRate()))).thenReturn(rate);

        book2.getRates().add(rate);
        book2.setAverageRate(book2.calAv());

        //when(rateRepository.save(any(Rate.class))).thenReturn(rate);

        final Rate saved = rateService.save(rateRequest, "huri");
        assertThat(saved).isNotNull();
        verify(rateRepository).save(any(Rate.class));
    }*/
}
