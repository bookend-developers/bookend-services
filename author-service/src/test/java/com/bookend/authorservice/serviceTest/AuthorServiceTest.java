package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.NotFoundException;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.model.Book;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.repository.AuthorRepository;
import com.bookend.authorservice.repository.BookRepository;
import com.bookend.authorservice.service.AuthorServiceImpl;
import com.bookend.authorservice.service.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.filter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private BookServiceImpl bookService;
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Spy
    private AuthorServiceImpl authorServiceSpy;
    @Test
    void shouldGetAuthorSuccesfully() throws NotFoundException {
        final String id = "ajsdhj23e";
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        given(authorRepository.findAuthorById(id)).willReturn(author);
        final Author expected = authorService.getById(id);
        assertThat(expected).isNotNull();
        assertEquals(author,expected);
    }
    @Test
    void failToGetByIDIfIdDoesNotMatchAnyAuthor() {
        final String id = "ajsdhj23e";
        given(authorRepository.findAuthorById(id)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            authorService.getById(id);
        });

    }
    @Test
    void failToUpdateAuthorBooksIfAuthorIdDoesNotMatchAnyExistingAuthor(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        final Book book = new Book("a4ds7dsf8vsd",author);
        final Map<String,String> msg =  new HashMap<>();
        msg.put("author","");
        msg.put("book","a4ds7dsf8vsd");
        when(authorRepository.findAuthorById(msg.get("author"))).thenReturn(null);
        assertThrows(NotFoundException.class, ()->{
            authorService.update(msg);
        });
        verify(authorRepository,never()).save(any(Author.class));

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldUpdateAuthorBooks() throws MandatoryFieldException, NotFoundException {
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        final Book book = new Book("a4ds7dsf8vsd",author);
        final Map<String,String> msg =  new HashMap<>();
        msg.put("author","ajsdhj23e");
        msg.put("book","a4ds7dsf8vsd");
        given(bookService.save(any(Book.class))).willReturn(book);
        when(authorRepository.findAuthorById(msg.get("author"))).thenReturn(author);
        given(authorRepository.save(any(Author.class))).willReturn(author);
        Mockito.doReturn(author).when(authorServiceSpy).getById(author.getId());
        final Author expected = authorService.update(msg);
        assertThat(expected).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }
    @Test
    void authorIsNotSavedIfNameFieldIsEmpty(){
        final AuthorRequest request = new AuthorRequest(null,"He is ..","1998-02-11" ,"2002-02-07");
        assertThrows(MandatoryFieldException.class,() -> {
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));


    }
    @Test
    void authorIsNotSavedIfBirthDateIsEmpty(){
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is..",null ,"2002-02-07");
        assertThrows(MandatoryFieldException.class,() -> {
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));
    }
    @Test
    void authorIsNotSavedIfBiographyIsEmpty(){
        final AuthorRequest request = new AuthorRequest("Ahmet Umit",null,"1998-02-11" ,"2002-02-07");
        assertThrows(MandatoryFieldException.class,() -> {
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));
    }
    @Test
    void authorIsNotSavedIfAuthorAlreadyExists(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),LocalDate.now());
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-11","2002-02-07");
        assertThrows(AuthorAlreadyExists.class,()->{
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldSaveIfAuthorRequestDeathDateIsNullAndNameBirthdayHaveMatchButDeathDayIsDifferent() throws MandatoryFieldException, AuthorAlreadyExists {
       final Author toBeSaved = new Author("ajsdhj23k","Ahmet Umit","He is ..", LocalDate.parse("1990-02-11"),null);
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),null);
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        given(authorRepository.save(any(Author.class))).willReturn(toBeSaved);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-11",null);
        final Author saved = authorService.save(request);
        assertThat(saved).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }
    @Test
    void shouldNotSaveIfAuthorRequestDeathDateIsNullAndNameBirthDayDeathDayAreSameWithRequest(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),null);
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),null);
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-11",null);
        assertThrows(AuthorAlreadyExists.class,()->{
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));
    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void shouldSaveIfAuthorRequestDeathDateIsNotNullAndNameBirthdayHaveMatchButDeathDayIsDifferent() throws MandatoryFieldException, AuthorAlreadyExists {

        final Author toBeSaved = new Author("ajsdhj23k","Ahmet Umit","He is ..", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-08"));
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),null);
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),null);
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        when(authorRepository.save(any(Author.class))).thenReturn(toBeSaved);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-11","2002-02-08");
        final Author saved = authorService.save(request);
        assertThat(saved).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }
    @Test
    void shouldNotSaveIfAuthorRequestDeathDateIsNotNullAndNameBirthDayDeathDayAreSameWithRequest()  {

        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),null);
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-11","2002-02-07");
        assertThrows(AuthorAlreadyExists.class,()->{
            authorService.save(request);
        });
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void shouldSaveWhenAuthorRequestOnlyNameHasMatchAndOtherFieldsAreDifferent() throws MandatoryFieldException, AuthorAlreadyExists {
        final Author toBeSaved = new Author("ajsdhj23k","Ahmet Umit","He is ..", LocalDate.parse("1990-04-14"),LocalDate.parse("2000-12-28"));
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),LocalDate.now());
        final List<Author> authors = Arrays.asList(author,author1);
        given(authorRepository.findByName(author.getName())).willReturn(authors);
        when(authorRepository.save(any(Author.class))).thenReturn(toBeSaved);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1990-04-14","2000-12-28");
        final Author saved = authorService.save(request);
        assertThat(saved).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }

    @Test
    void shouldReturnAllAuthor(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long life ..", LocalDate.now(),LocalDate.now());
        final Author author1 = new Author("ajsdhj24e","Selcuk Aydemir","Aydemir was born..", LocalDate.now(),LocalDate.now());
        final Author author2= new Author("ajsdhj21e","Andy Weir","Andy is ...", LocalDate.now(),LocalDate.now());
        final List<Author> authors = Arrays.asList(author,author1,author2);
        given(authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).willReturn(authors);
        final List<Author> expected = authorService.getAll();
        assertEquals(authors,expected);
    }
    @Test
    void shouldReturnAllAuthorMatchesGivenTitle(){
        final String title = "umit";
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.of(1998,01,11),null);
        List<Author> authors = Arrays.asList(author);
        given(authorRepository.findByNameContainingIgnoreCase(title)).willReturn(authors);
        final List<Author> expected = authorService.search(title);
        assertEquals(authors,expected);

    }
}
