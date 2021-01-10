package com.bookend.authorservice.serviceTest;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.AuthorNotFound;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.repository.AuthorRepository;
import com.bookend.authorservice.service.AuthorService;
import com.bookend.authorservice.service.AuthorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    @InjectMocks
    private AuthorServiceImpl authorService;
    @Test
    void shouldGetAuthorSuccesfully() throws AuthorNotFound {
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
        assertThrows(AuthorNotFound.class,()->{
            authorService.getById(id);
        });

    }
    @Test
    void shouldUpdateAuthor(){
        final Author author = new Author("ajsdhj23e","Ahmet Umit");
        given(authorRepository.save(author)).willReturn(author);
        final Author expected = authorService.update(author);
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
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),LocalDate.now());
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
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),LocalDate.now());
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
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldSaveWhenAuthorRequestNameAndBirthdayHaveMatchAndDeathDayIsDifferent() throws MandatoryFieldException, AuthorAlreadyExists {
        final Author s =new Author("ajsdhj23k","Ahmet Umit","He is ..", LocalDate.parse("1990-02-12"),LocalDate.parse("2000-12-28"));
        final Author toBeSaved = new Author("Ahmet Umit","He is ..", LocalDate.parse("1990-02-12"),LocalDate.parse("2000-12-28"));
        final Author author = new Author("ajsdhj23e","Ahmet Umit","Long", LocalDate.parse("1998-02-11"),LocalDate.parse("2002-02-07"));
        final Author author1 = new Author("ajsdhj24e","Ahmet Umit","Umit was born..", LocalDate.now(),LocalDate.now());
        final List<Author> authors = Arrays.asList(author,author1);
        when(authorRepository.findByName(toBeSaved.getName())).thenReturn(authors);
        final AuthorRequest request = new AuthorRequest("Ahmet Umit","He is ..","1998-02-12","2000-12-28");
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));
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
