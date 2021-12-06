package com.ratecommentservice.serviceTest;

import com.ratecommentservice.exception.BookNotFound;
import com.ratecommentservice.model.Book;
import com.ratecommentservice.repository.BookRepository;
import com.ratecommentservice.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;


    @Test
    void shouldSaveGivenBookSuccessfully(){
        String id = "5";
        final Book book = new Book(id,"Yuzbasının Kızı");
        given(bookRepository.save(any(Book.class))).willReturn(book);
        final Book saved = bookService.save(book);
        assertThat(saved).isNotNull();
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void getBookIfBookIdHaveMatchSuccesfully() throws BookNotFound {
        String id = "5";
        final Book book = new Book(id,"Yuzbasının Kızı");
        given(bookRepository.findBookByBookId(id)).willReturn(book);
        final Book expected = bookService.findBookByBookID(id);
        assertThat(expected).isNotNull();
        assertEquals(book,expected);
    }

    @Test
    void failToGetBookIfBookIdDoesNotMatch() throws BookNotFound {
        final String id = "5";
        given(bookRepository.findBookByBookId(id)).willReturn(null);
        assertThrows(BookNotFound.class,()->{
            bookService.findBookByBookID(id);
        });
    }


    //getAverageRate inin null olma durumu kontrol edilmesi gerekiyor testte, rate verilmemiş olabilir çünkü
    @Test
    void shouldFindAllBookIds(){
        List<Book> bookList = new ArrayList<>();
        final Book book1 = new Book("5","Yuzbasının Kızı");
        book1.setAverageRate(5.0);
        final Book book2 = new Book("6","Madame Bovary");
        book2.setAverageRate(4.0);
        bookList.add(book1);
        bookList.add(book2);
        //given(bookRepository.findAll()).willReturn(bookList);
        given(bookRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Book::getAverageRate).reversed())
                .collect(Collectors.toList())).willReturn(bookList);
        List<String> bookids = bookList.stream().map(book -> book.getBookid()).collect(Collectors.toList());
        final List<String> expected = bookService.findAll();
        assertEquals(bookids,expected);
    }

}
