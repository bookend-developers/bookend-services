package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.repository.BookRepository;
import com.bookend.authorservice.service.BookServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    @Spy
    private BookServiceImpl bookServiceSpy;
    @Test
    void shouldReturnBookWithGivenId() throws NotFoundException {
        final String id = "2dfa8sd92hjhaf";
        final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        given(bookRepository.findByBookId(id)).willReturn(book);
        final Book expected = bookService.findByBookid(id);
        assertThat(expected).isNotNull();
        assertEquals(expected,book);
    }
    @Test
    void shouldSaveBook() throws MandatoryFieldException {
        final String id = "2dfa8sd92hjhaf";
        final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        given(bookRepository.save(book)).willReturn(book);
        final Book expected = bookService.save(book);
        assertThat(expected).isNotNull();
        assertEquals(expected,book);

    }
    @Test
    void shouldFailReturnBookWithGivenId()  {
        final String id = "2dfa8sd92hjhaf";
       // final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        given(bookRepository.findByBookId(id)).willReturn(null);

        assertThrows(NotFoundException.class,()->{
            bookService.findByBookid(id);
        });
    }
    @Test
    void shouldFailSaveBookIfIDIsEmpty() throws MandatoryFieldException {
        final Book book = new Book(null,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(book);
        });
        verify(bookRepository,never()).save(any(Book.class));

    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void shouldDelete() throws NotFoundException {
        final String id = "2dfa8sd92hjhaf";
        final Book book = new Book(id,new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now()));

        doReturn(book).when(bookServiceSpy).findByBookid(id);
        when(bookRepository.findByBookId(id)).thenReturn(book);
        bookService.deleteByBookId(id);
        verify(bookRepository,times(1)).deleteByBookId(id);
    }


}
