package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.repository.AuthorRepository;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.filter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Test
    void getAuthorByIdTest(){
        final String id = "ajsdhj23e";
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        given(authorRepository.findAuthorById(id)).willReturn(author);
        final Author expected = authorService.getById(id);
        assertThat(expected).isNotNull();
        assertEquals(author,expected);
    }
    @Test
    void updateAuthorTest(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        given(authorRepository.save(author)).willReturn(author);
        final Author expected = authorService.update(author);
        assertThat(expected).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }
    @Test
    void saveTestFail(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now());
        given(authorRepository.findByName(author.getName())).willReturn(Arrays.asList(author));
        final Author expected = authorService.save(author);
        assertThat(expected).isNull();

    }
    @Test
    void saveTestNullDeathSuccess(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),null);
        given(authorRepository.findByName(author.getName())).willReturn(Arrays.asList(author));
        final Author newAuthor = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now());
        given(authorRepository.save(newAuthor)).willReturn(newAuthor);
        final Author expected = authorService.save(newAuthor);
        assertThat(expected).isNotNull();

    }
    @Test
    void saveTestSuccess(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),null);
        given(authorRepository.findByName(author.getName())).willReturn(new ArrayList<>());
        given(authorRepository.save(author)).willReturn(author);
        final Author expected = authorService.save(author);
        assertThat(expected).isNotNull();

    }
    @Test
    void saveTestDeathDaySuccess(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.of(1992,1,21));
        given(authorRepository.findByName(author.getName())).willReturn(Arrays.asList(author));
        final Author newAuthor = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now());
        given(authorRepository.save(newAuthor)).willReturn(newAuthor);
        final Author expected = authorService.save(newAuthor);
        assertThat(expected).isNotNull();

    }
    @Test
    void saveTestBirthdaySuccess(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.of(1998,01,11),null);
        given(authorRepository.findByName(author.getName())).willReturn(Arrays.asList(author));
        final Author newAuthor = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.now(),LocalDate.now());
        given(authorRepository.save(newAuthor)).willReturn(newAuthor);
        final Author expected = authorService.save(newAuthor);
        assertThat(expected).isNotNull();

    }
    @Test
    void getAll(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long life ..", LocalDate.now(),LocalDate.now());
        final Author author1 = new Author("ajsdhj24e","Selcuk Aydemir","Aydemir was born..", LocalDate.now(),LocalDate.now());
        final Author author2= new Author("ajsdhj21e","Andy Weir","Andy is ...", LocalDate.now(),LocalDate.now());
        final List<Author> authors = Arrays.asList(author,author1,author2);
        given(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).willReturn(authors);
        final List<Author> expected = authorService.getAll();
        assertEquals(authors,expected);
    }
    @Test
    void searchTest(){
        final String title = "umit";
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.of(1998,01,11),null);
        List<Author> authors = Arrays.asList(author);
        given(authorRepository.findByNameContainingIgnoreCase(title)).willReturn(authors);
        final List<Author> expected = authorService.search(title);
        assertEquals(authors,expected);

    }
}
