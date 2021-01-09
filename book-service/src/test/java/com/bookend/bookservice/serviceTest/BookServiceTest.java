package com.bookend.bookservice.serviceTest;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.model.KafkaMessage;
import com.bookend.bookservice.model.SortedLists;
import com.bookend.bookservice.payload.BookRequest;
import com.bookend.bookservice.repository.BookRepository;
import com.bookend.bookservice.service.BookServiceImpl;
import com.bookend.bookservice.service.GenreService;
import com.bookend.bookservice.service.SortService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final String BOOK_TOPIC = "adding-book";
    private static final String DELETE_TOPIC = "deleting-book";
    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreService genreService;
    @Mock
    private SortService sortService;
    @Mock
    private Producer producer;
    @InjectMocks
    private BookServiceImpl bookService;
    @Test
    void shouldReturnBookWithGivenId() throws NotFoundException {
        final String id = "ash2jhs45";
        final Genre genre = new Genre("5asd23dfgf","Classics");
        final Book book = new Book(Integer.valueOf("123"),genre,"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        given(bookRepository.findBookById(id)).willReturn(book);
        final Book expected = bookService.getById(id);
        assertThat(expected).isNotNull();
        assertEquals(book,expected);

    }
    @Test
    void shouldFailToReturnIfBookDoesNotExistWithGivenId(){
        final String id = "ash2jhs45";
        given(bookRepository.findBookById(id)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.getById(id);
        });
    }
    @Test
    void shouldReturnAllBooks(){
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        given(bookRepository.findAll(Sort.by(Sort.Direction.ASC,"bookName"))).willReturn(books);
        final List<Book> expected = bookService.getAll();
        assertThat(expected).isNotNull();
        assertEquals(books,expected);
    }
    @Test
    void shouldReturnAuthorsBooksWithGivenId() throws NotFoundException {
        String authorid = "45afs34";
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",false,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Ivan Gonçarov","45afs34",false,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Ivan Gonçarov","45afs34",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        given(bookRepository.findByAuthorid(authorid)).willReturn(books);
        final List<Book> expected = bookService.findByAuthor(authorid);
        assertThat(expected).isNotNull();
        assertEquals(books,expected);
    }
    @Test
    void shouldFailToReturnAuthorsBooksIfNoBookExistsWithGivenAuthorID(){
        String authorid = "45afs34";
        given(bookRepository.findByAuthorid(authorid)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.findByAuthor(authorid);
        });
    }
    @Test
    void shouldReturnAllBookThatAreNotVerified() throws NotFoundException {
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        given(bookRepository.findBookByVerifiedIsFalse()).willReturn(books);
        final List<Book> expected = bookService.findBookByVerifiedIsFalse();
        assertThat(expected).isNotNull();
        assertEquals(books,expected);
    }
    @Test
    void shouldFailToReturnUnverifiedBooksIfNoBookisUnverified(){
        given(bookRepository.findBookByVerifiedIsFalse()).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.findBookByVerifiedIsFalse();
        });
    }
    @Test
    void shouldDeleteBookWithGivenId() throws NotFoundException {
        final String bookId = "ash2jhs45";
        final Genre genre = new Genre("5asd23dfgf","Classics");
        final Book book = new Book(Integer.valueOf("123"),genre,"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        given(bookRepository.findBookById(bookId)).willReturn(book);
        bookService.delete(bookId);
        verify(bookRepository,times(1)).delete(any(Book.class));
    }

    @Test
    void shouldFailToDeleteBookIfNoBookExistsWithGivenId(){
        final String bookId = "ash2jhs45";
        given(bookRepository.findBookById(bookId)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.delete(bookId);
        });
    }
    @Test
    void shouldFailToSaveIfBookNameIsEmptyString(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","....",true,"","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
           bookService.save(request);
        });
    }
    @Test
    void shouldFailToSaveIfBookNameIsNull(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","....",true,null,"Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });

    }
    @Test
    void shouldFailToSaveIfAuthorIsEmptyString(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","....",true,"Oblomov","","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });
    }
    @Test
    void shouldFailToSaveIfAuthorIsNull(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","....",true,"Oblomov",null,"asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });

    } @Test
    void shouldFailToSaveIfDescriptionIsEmptyString(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","",true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });
    }
    @Test
    void shouldFailToSaveIfDescriptionIsNull(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics",null,true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });

    }
    @Test
    void shouldFailToSaveIfPageIsNull(){
        final BookRequest request = new BookRequest(null,"Classics","....",true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });

    }
    @Test
    void shouldFailToSaveIfISBNIsEmptyString(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","...",true,"Oblomov","Ivan Gonçarov","asfd54adsd","");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });
    }
    @Test
    void shouldFailToSaveIfISBNIsNull(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","...",true,"Oblomov","Ivan Gonçarov","asfd54adsd",null);
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });

    }
    @Test
    void shouldFailToSaveIfGenreIsEmptyString(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),"","....",true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });
    }
    @Test
    void shouldFailToSaveIfGenreIsNull(){
        final BookRequest request = new BookRequest(Integer.valueOf("123"),null,"...",true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");
        assertThrows(MandatoryFieldException.class,()->{
            bookService.save(request);
        });
    }
    @Test
    void shouldSaveIfBookNameHaveMatchButAuthorIsDifferent() throws AlreadyExist, MandatoryFieldException {
        final String name = "Günlükler";
        final Genre journal = new Genre("5asd25dfgf","Journal");
        final Genre classics = new Genre("5asd25dfg4f","Classics");
        final BookRequest request = new BookRequest(Integer.valueOf("456"),"Classics","...",true,"Günlükler","Oguz Atay","45af5s34","1234567271234");
        final Book tobeSaved = new Book("ash1jhs45",Integer.valueOf("456"),classics,"...","Günlükler","Oguz Atay","45af5s34",true,"1234567271234");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),journal,"...","Günlükler","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),journal,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),journal,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        when(bookRepository.findBookByBookName(name)).thenReturn(books);
        when(genreService.findByGenre(request.getGenre())).thenReturn(null);
        when(genreService.addNewGenre(request.getGenre())).thenReturn(classics);
        when(bookRepository.save(any(Book.class))).thenReturn(tobeSaved);
        final Book expected = bookService.save(request);
        assertThat(expected).isNotNull();
        verify(bookRepository).save(any(Book.class));
    }
    @Test
    void shouldSaveIfBookNameAuthorHaveMatchButGenreIsDifferent() throws AlreadyExist, MandatoryFieldException {
        final String name = "Günlükler";
        final Genre genre = new Genre("5asd25dfgf","Journal");
        final BookRequest request = new BookRequest(Integer.valueOf("456"),"Journal","...",true,"Günlükler","Oguz Atay","45af5s34","1234567271234");
        final Book tobeSaved = new Book("ash1jhs45",Integer.valueOf("456"),genre,"...","Günlükler","Oguz Atay","45af5s34",true,"1234567271234");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Fiction"),"...","Günlükler","Oguz Atay","45af5s34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),genre,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),genre,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        when(bookRepository.findBookByBookName(name)).thenReturn(books);
        when(genreService.findByGenre(request.getGenre())).thenReturn(genre);
        when(bookRepository.save(any(Book.class))).thenReturn(tobeSaved);
        final Book expected = bookService.save(request);
        assertThat(expected).isNotNull();
        verify(bookRepository).save(any(Book.class));
    }
    @Test
    void shouldSaveIfBookNameAuthorGenreHaveMatchButISBNIsDifferent() throws AlreadyExist, MandatoryFieldException {
        final String name = "Günlükler";
        final Genre genre = new Genre("5asd25dfgf","Journal");
        final BookRequest request = new BookRequest(Integer.valueOf("456"),"Journal","...",true,"Günlükler","Oguz Atay","45af5s34","1234567271234");
        final Book tobeSaved = new Book("ash1jhs45",Integer.valueOf("456"),genre,"...","Günlükler","Oguz Atay","45af5s34",true,"1234567271234");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),genre,"...","Günlükler","Oguz Atay","45af5s34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),genre,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),genre,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        when(bookRepository.findBookByBookName(name)).thenReturn(books);
        when(genreService.findByGenre(request.getGenre())).thenReturn(genre);
        when(bookRepository.save(any(Book.class))).thenReturn(tobeSaved);
        final Book expected = bookService.save(request);
        assertThat(expected).isNotNull();
        verify(bookRepository).save(any(Book.class));
    }
    @Test
    void shouldNotSaveIfBookNameAuthorGenreISBNHaveMatch(){
        final String name = "Günlükler";
        final BookRequest request = new BookRequest(Integer.valueOf("456"),"Journal","...",true,"Günlükler","Oguz Atay","45af5s34","1234567891234");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd25dfgf","Journal"),"...","Günlükler","Oguz Atay","45af5s34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        when(bookRepository.findBookByBookName(name)).thenReturn(books);
        assertThrows(AlreadyExist.class,()->{
            bookService.save(request);
        });
        verify(bookRepository,never()).save(any(Book.class));
    }
    @Test
    void shouldReturnSortedBooksByRate() throws NotFoundException {
        final SortedLists sortedLists = new SortedLists();
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> sortedByRate = Arrays.asList(book0,book1,book2);
        final List<Book> sortedByComment = Arrays.asList(book1,book1,book0);
        sortedLists.setSortedByComment(sortedByComment);
        sortedLists.setSortedByRate(sortedByRate);
        when(sortService.findOne()).thenReturn(sortedLists);
        final List<Book> expected = bookService.search(null,null,true,false);
        assertThat(expected).isNotNull();



    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldReturnAllBooksIfGivenSearchFieldsAreFalseOrNull() throws NotFoundException {

        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        BookServiceImpl bookspy = Mockito.spy(new BookServiceImpl());
        Mockito.doReturn(books).when(bookspy).getAll();
        final List<Book> expected = bookService.search(null,null,false,false);
        assertThat(expected).isNotNull();
        verify(sortService,never()).findOne();

    }
    @Test
    void shouldReturnSortedBooksByNumberOfComments() throws NotFoundException {
        final SortedLists sortedLists = new SortedLists();
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> sortedByRate = Arrays.asList(book0,book1,book2);
        final List<Book> sortedByComment = Arrays.asList(book1,book1,book0);
        sortedLists.setSortedByComment(sortedByComment);
        sortedLists.setSortedByRate(sortedByRate);
        when(sortService.findOne()).thenReturn(sortedLists);
        final List<Book> expected = bookService.search(null,null,false,true);
        assertThat(expected).isNotNull();


    }
    @MockitoSettings(strictness = Strictness.LENIENT)
    @Test
    void shouldReturnBooksFilteredByGivenTitle() throws NotFoundException {
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        BookServiceImpl bookspy = Mockito.spy(new BookServiceImpl());
        Mockito.doReturn(books).when(bookService).getAll();
        final List<Book> expected = bookService.search("Ob",null,false,false);
        assertThat(expected).isNotNull();
        verify(sortService,never()).findOne();

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldReturnBooksFilteredByGenre() throws NotFoundException {
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        BookServiceImpl bookspy = Mockito.spy(new BookServiceImpl());
        Mockito.doReturn(books).when(bookspy).getAll();
        final List<Book> expected = bookService.search(null,"Classics",false,false);
        assertThat(expected).isNotNull();
        verify(sortService,never()).findOne();
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToReturnBooksFilteredByGivenTitle()  {
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        BookServiceImpl bookspy = Mockito.spy(new BookServiceImpl());
        Mockito.doReturn(books).when(bookspy).getAll();
        assertThrows(NotFoundException.class,()->{
            bookService.search("Kacıs",null,false,false);
        });
        verify(sortService,never()).findOne();
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToReturnBooksFilteredByGenre(){
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),new Genre("5asd23dfgf","Classics"),"...","Oblomov","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),new Genre("5asd24dsdgf","Fiction"),".....","Cocuk Kalbi","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),new Genre("5asd25dfgf","Journal"),".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> books = Arrays.asList(book0,book1,book2);
        BookServiceImpl spy = Mockito.spy(new BookServiceImpl());
        Mockito.doReturn(books).when(spy).getAll();
        assertThrows(NotFoundException.class,()->{
            bookService.search(null,"Science-Fiction",false,false);
        });
        verify(sortService,never()).findOne();
    }



    //final BookRequest request = new BookRequest(Integer.valueOf("123"),"Classics","....",true,"Oblomov","Ivan Gonçarov","asfd54adsd","123654789123");



}
