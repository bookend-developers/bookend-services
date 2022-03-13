package com.bookend.shelfservice.serviceTest;

import com.bookend.shelfservice.exception.MandatoryFieldException;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.ShelfRequest;
import com.bookend.shelfservice.repository.ShelfRepository;
import com.bookend.shelfservice.repository.TagRepository;
import com.bookend.shelfservice.service.ShelfServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShelfServiceTest {
    @Mock
    private ShelfRepository shelfRepository;
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private ShelfServiceImpl shelfService;

    @Test
    void getShelfIfIdHaveMatchSuccesfully() throws ShelfNotFound {
        final Long id = Long.valueOf(5);
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Romantic"));
        final Shelf shelf = new Shelf(id,"Recently Read","eda", tags);
        given(shelfRepository.findShelfById(id)).willReturn(shelf);
        final Shelf expected = shelfService.getById(id);
        assertThat(expected).isNotNull();
        assertEquals(shelf,expected);
    }

    @Test
    void failToGetShelfIfIdDoesNotMatch() throws ShelfNotFound {
        final Long id = Long.valueOf(5);
        given(shelfRepository.findShelfById(id)).willReturn(null);
        assertThrows(ShelfNotFound.class,()->{
            shelfService.getById(id);
        });
    }

    @Test
    void shouldSaveShelfWithEmptyTagList() throws MandatoryFieldException {
        final Shelf shelf = new Shelf("Recently Read","eda");
        List<String> tags = new ArrayList<>();
        final ShelfRequest shelfRequest = new ShelfRequest("Recently Read", tags);
        given(shelfRepository.save(any(Shelf.class))).willReturn(shelf);
        final Shelf saved = shelfService.saveOrUpdate(shelfRequest, "eda");
        assertThat(saved).isNotNull();
        verify(shelfRepository).save(any(Shelf.class));

    }

    @Test
    void failToSaveShelfIfShelfNameIsEmpty(){
        final ShelfRequest shelfRequest = new ShelfRequest(null, new ArrayList<String>());
        assertThrows(MandatoryFieldException.class,() -> {
            shelfService.saveOrUpdate(shelfRequest, "eda");
        });
        verify(shelfRepository, never()).save(any(Shelf.class));
    }

    @Test
    void shouldReturnNullIfShelfNameAlreadyExists() throws MandatoryFieldException {
        List<Shelf> shelves = new ArrayList<>();
        final Shelf shelf = new Shelf("Recently Read","eda");
        final Shelf shelf2 = new Shelf("To Read","eda");
        shelves.add(shelf);
        shelves.add(shelf2);
        final ShelfRequest shelfRequest = new ShelfRequest("To Read", new ArrayList<String>());
        given(shelfRepository.findShelvesByUsername("eda")).willReturn(shelves);
        final Shelf saved = shelfService.saveOrUpdate(shelfRequest, "eda");
        assertThat(saved).isNull();
    }
    //---------------Bakılsın tekrar---------------------------
    @Test
    void shouldSaveShelfSuccesfully() throws ShelfNotFound, MandatoryFieldException {
        List<String> tagNames = new ArrayList<>();
        tagNames.add("Romantic");
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("Romantic");
        given(tagRepository.findByTag("Romantic")).willReturn(tag);
        tags.add(tag);
        final ShelfRequest shelfRequest = new ShelfRequest("To Read",tagNames);
        final Shelf shelf = new Shelf(shelfRequest.getShelfname(),"eda", tags);
        given(shelfRepository.save(any(Shelf.class))).willReturn(shelf);
        final Shelf saved = shelfService.saveOrUpdate(shelfRequest, "eda");
        assertThat(saved).isNotNull();
        verify(shelfRepository).save(any(Shelf.class));
    }

    @Test
    void shouldReturnAllShelvesWithGivenUsername(){
        List<Shelf> shelves = new ArrayList<>();
        final Shelf shelf = new Shelf("Recently Read","eda");
        final Shelf shelf2 = new Shelf("To Read","eda");
        shelves.add(shelf);
        shelves.add(shelf2);
        given(shelfRepository.findShelvesByUsername("eda")).willReturn(shelves);
        final List<Shelf> expected = shelfService.findShelvesByUsername("eda");
        assertEquals(shelves,expected);
    }

    @Test
    void shouldDeleteShelfWithGivenShelf() throws NotFoundException {
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        //given(shelfRepository.findShelfById(id)).willReturn(shelf);
        shelfService.deleteShelf(shelf);
        verify(shelfRepository,times(1)).delete(any(Shelf.class));
    }

/*
    @Test
    void shouldFailToDeleteShelfIfNoShelfExistsWithGivenShelf() {
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        when(shelfRepository.findShelfById(id)).thenReturn(null);
        assertThrows(NotFoundException.class,()->{
            shelfService.deleteShelf(shelf);
        });
    }
*/
    @Test
    void shouldGetBooksWithGivenShelfId() throws ShelfNotFound {
        List<ShelfsBook> books = new ArrayList<>();
        final Long id = Long.valueOf(5);
        final Shelf shelf = new Shelf(id,"Recently Read","eda", new ArrayList<Tag>());
        ShelfsBook shelfsBook = new ShelfsBook("abcdef2121", "Sefiller", shelf);
        books.add(shelfsBook);
        shelf.setShelfsBooks(books);
        given(shelfRepository.findShelfById(id)).willReturn(shelf);
        //given(shelfRepository.findShelfById(id).getShelfsBooks()).willReturn(books);
        final List<ShelfsBook> expected = shelfService.getBooks(id);
        assertThat(expected).isNotNull();
        assertEquals(books,expected);
    }

 

}
