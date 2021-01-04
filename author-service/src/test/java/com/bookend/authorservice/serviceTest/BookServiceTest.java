package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.repository.BookRepository;
import com.bookend.authorservice.service.BookServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    @Test
    void shouldReturnBookWithGivenId(){
        final String id = "2dfa8sd92hjhaf";
        final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        given(bookRepository.findByBookId(id)).willReturn(book);
        final Book expected = bookService.findByBookid(id);
        assertThat(expected).isNotNull();
        assertEquals(expected,book);
    }
    @Test
    void shouldSaveBook(){
        final String id = "2dfa8sd92hjhaf";
        final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        given(bookRepository.save(book)).willReturn(book);
        final Book expected = bookService.save(book);
        assertThat(expected).isNotNull();
        assertEquals(expected,book);

    }
    @Test
    void shouldDelete(){
        final String id = "2dfa8sd92hjhaf";
        bookService.deleteByBookId(id);
        verify(bookRepository,times(1)).deleteByBookId(id);
    }


}
