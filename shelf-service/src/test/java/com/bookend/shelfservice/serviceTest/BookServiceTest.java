package com.bookend.shelfservice.serviceTest;

import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.exception.ShelfsBookNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.repository.BookRepository;
import com.bookend.shelfservice.repository.ShelfRepository;
import com.bookend.shelfservice.service.BookServiceImpl;
import com.bookend.shelfservice.service.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ShelfRepository shelfRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getShelfsBookIfIdHaveMatchSuccesfully() throws ShelfsBookNotFound {
        String id = "5";
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Romantic"));
        final Shelf shelf = new Shelf("Recently Read","eda", tags);
        final ShelfsBook shelfsBook = new ShelfsBook(id, "The Fairy Tales",shelf);
        given(bookRepository.findBookById(Long.valueOf(id))).willReturn(shelfsBook);
        final ShelfsBook expected = bookService.getById(id);
        assertThat(expected).isNotNull();
        assertEquals(shelfsBook,expected);
    }

    @Test
    void failToGetShelfsBooksIfIdDoesNotMatch() throws ShelfsBookNotFound {
        final Long id = Long.valueOf(5);
        given(bookRepository.findBookById(id)).willReturn(null);
        assertThrows(ShelfsBookNotFound.class,()->{
            bookService.getById(id.toString());
        });
    }

    @Test
    void shouldReturnNullIfGivenShelfsBookThatWillBeSavedAlreadyExists(){
        List<ShelfsBook> books = new ArrayList<>();
        String id = "5";
        final Shelf shelf = new Shelf("Recently Read","eda");
        final ShelfsBook shelfsBook = new ShelfsBook(id, "The Fairy Tales",shelf);
        books.add(shelfsBook);
        final ShelfsBook shelfsBook2 = new ShelfsBook(id, "The Tales",shelf);
        given(bookRepository.findShelfsBookByShelf(shelf)).willReturn(books);
        final ShelfsBook saved = bookService.saveOrUpdate(shelfsBook2);
        assertThat(saved).isNull();
    }

    @Test
    void shouldSaveGivenShelfsBookSuccessfully(){
        String id = "5";
        final Shelf shelf = new Shelf("Recently Read","eda");
        final ShelfsBook shelfsBook = new ShelfsBook(id, "The Fairy Tales",shelf);
        given(bookRepository.save(any(ShelfsBook.class))).willReturn(shelfsBook);
        final ShelfsBook saved = bookService.saveOrUpdate(shelfsBook);
        assertThat(saved).isNotNull();
        verify(bookRepository).save(any(ShelfsBook.class));
    }

    @Test
    void shouldDeleteBookWithGivenShelfIdAndBookId() throws NotFoundException {
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        final ShelfsBook shelfsBook = new ShelfsBook("2", "The Fairy Tales",shelf);
        given(shelfRepository.findShelfById(id)).willReturn(shelf);
        given(bookRepository.findByBookIDAndShelf("2",shelf)).willReturn(shelfsBook);
        bookService.delete("2",id.toString());
        verify(bookRepository,times(1)).delete(any(ShelfsBook.class));
    }
    @Test
    void failToDeleteShelfWithGivenShelfDoesNotExist() throws NotFoundException {
        final Long id = Long.valueOf(5);
        given(shelfRepository.findShelfById(id)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.delete("2",id.toString());
        });
    }
    @Test
    void failToDeleteShelfWithGivenShelfsBookDoesNotExist() throws NotFoundException {
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        given(shelfRepository.findShelfById(id)).willReturn(shelf);
        given(bookRepository.findByBookIDAndShelf("2",shelf)).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.delete("2",id.toString());
        });
    }
    @Test
    void shouldDeleteBookFromAllShelves() throws NotFoundException {
        List<ShelfsBook> shelfsBooks = new ArrayList<>();
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        final ShelfsBook shelfsBook = new ShelfsBook("2", "The Fairy Tales",shelf);
        shelfsBooks.add(shelfsBook);
        given(bookRepository.findShelfsBookByBookID("2")).willReturn(shelfsBooks);
        bookService.deleteFromShelves("2");
        verify(bookRepository,times(1)).delete(any(ShelfsBook.class));
    }

    @Test
    void failToDeleteBookFromAllShelvesIfBookDoesNotExistInShelves() throws NotFoundException {
        given(bookRepository.findShelfsBookByBookID("2")).willReturn(null);
        assertThrows(NotFoundException.class,()->{
            bookService.deleteFromShelves("2");
        });
    }


}
