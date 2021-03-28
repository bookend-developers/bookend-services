package com.bookend.bookservice.serviceTest;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.model.SortedLists;
import com.bookend.bookservice.repository.SortedListRepo;
import com.bookend.bookservice.service.SortServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
public class SortServiceTest {
    @Mock
    private SortedListRepo sortedListRepo;
    @Mock
    private SortedLists sortedListsMock;
    @InjectMocks
    private SortServiceImpl sortService;
    @Spy
    private SortServiceImpl sortServiceSpy;
    @Test
    void shouldFindOneIfASortedListsAlreadyExist(){
        final SortedLists sortedLists = new SortedLists();
        when(sortedListRepo.count()).thenReturn(Long.valueOf(1));
        when(sortedListRepo.findAll()).thenReturn(Arrays.asList(sortedLists));
        final SortedLists expected = sortService.findOne();
        assertThat(expected).isNotNull();
        assertEquals(expected,sortedLists);
        verify(sortedListRepo,never()).save(any(SortedLists.class));
    }
    @Test
    void shouldNotSaveWhenFindOneCalledAndIfCountGreaterThenOne(){
        final SortedLists sortedLists = new SortedLists();
        final SortedLists sortedLists2 = new SortedLists();
        when(sortedListRepo.count()).thenReturn(Long.valueOf(2));
        when(sortedListRepo.findAll()).thenReturn(Arrays.asList(sortedLists,sortedLists2));
        final SortedLists expected = sortService.findOne();
        assertThat(expected).isNotNull();
        assertEquals(expected,sortedLists);
        verify(sortedListRepo,never()).save(any(SortedLists.class));
    }
    @Test
    void shouldFindOneThatIsNewCreatedIfNoSortedListExists(){
        final SortedLists sortedLists = new SortedLists();
        when(sortedListRepo.count()).thenReturn(Long.valueOf(0));
        when(sortedListRepo.save(any(SortedLists.class))).thenReturn(sortedLists);
        final SortedLists expected = sortService.findOne();
        assertThat(expected).isNotNull();
        assertEquals(expected,sortedLists);
        verify(sortedListRepo,times(1)).save(any(SortedLists.class));
    }
    @Test
    void shouldRemoveBookInTheSortedLists(){
        final Genre journal = new Genre("5asd25dfgf","Journal");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),journal,"...","Günlükler","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),journal,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),journal,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> sortedByRate = new ArrayList(Arrays.asList(book0,book1,book2));
        final List<Book> sortedByComment = new ArrayList(Arrays.asList(book0,book1,book2));
        final SortedLists sortedLists = new SortedLists(sortedByRate,sortedByComment,"target");
        doReturn(sortedLists).when(sortServiceSpy).findOne();
        when(sortedListRepo.count()).thenReturn(Long.valueOf(1));
        when(sortedListRepo.findAll()).thenReturn(new ArrayList(Arrays.asList(sortedLists)));
        when(sortedListRepo.save(sortedLists)).thenReturn(sortedLists);
        final SortedLists expected = sortService.remove(book0);
        assertThat(expected.getSortedByComment()).doesNotContain(book0);
        assertThat(expected.getSortedByRate()).doesNotContain(book0);
    }
    @Test
    void shouldSortSortedListWithGivenTypeRate(){
        final List<Long> comments0 = new ArrayList(Arrays.asList(123,1231,1231,1221131,12313));
        final List<Long> comments1 = new ArrayList(Arrays.asList(1233,12321));
        final List<Long> comments2 = new ArrayList(Arrays.asList(1234,15231,17231,12213));
        final Genre journal = new Genre("5asd25dfgf","Journal");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),journal,"...","Günlükler","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),journal,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),journal,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        book0.setRate(Double.valueOf(4.6));
        book0.setComments(comments0);
        book1.setRate(Double.valueOf(2.6));
        book1.setComments(comments1);
        book2.setRate(Double.valueOf(4.7));
        book2.setComments(comments2);
        final List<Book> sortedByRate = new ArrayList(Arrays.asList(book1,book0,book2));
        final List<Book> sortedByComment = new ArrayList(Arrays.asList(book1,book2,book0));
        List<Book> shuffledRate = new ArrayList(sortedByRate);
        List<Book> shuffledComment = new ArrayList(sortedByComment);
        Collections.shuffle(shuffledComment);
        Collections.shuffle(shuffledRate);
        final SortedLists expected = new SortedLists(sortedByRate,sortedByComment,"target");
        final SortedLists sortedLists = new SortedLists(shuffledRate,shuffledComment,"target");
        when(sortedListRepo.count()).thenReturn(Long.valueOf(1));
        when(sortedListRepo.findAll()).thenReturn(new ArrayList(Arrays.asList(sortedLists)));
        when(sortedListRepo.save(sortedLists)).thenReturn(sortedLists);
        final SortedLists returned = sortService.sort("rate");
        assertEquals(expected.getSortedByRate(),returned.getSortedByRate());
    }
    @Test
    void shouldSortSortedListWithGivenTypeComment(){
        final List<Long> comments0 = new ArrayList(Arrays.asList(123,1231,1231,1221131,12313));
        final List<Long> comments1 = new ArrayList(Arrays.asList(1233,12321));
        final List<Long> comments2 = new ArrayList(Arrays.asList(1234,15231,17231,12213));
        final Genre journal = new Genre("5asd25dfgf","Journal");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),journal,"...","Günlükler","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),journal,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),journal,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        book0.setRate(Double.valueOf(4.6));
        book0.setComments(comments0);
        book1.setRate(Double.valueOf(2.6));
        book1.setComments(comments1);
        book2.setRate(Double.valueOf(4.7));
        book2.setComments(comments2);
        final List<Book> sortedByRate = new ArrayList(Arrays.asList(book1,book0,book2));
        final List<Book> sortedByComment = new ArrayList(Arrays.asList(book1,book2,book0));
        List<Book> shuffledRate = new ArrayList(sortedByRate);
        List<Book> shuffledComment = new ArrayList(sortedByComment);
        Collections.shuffle(shuffledComment);
        Collections.shuffle(shuffledRate);
        final SortedLists expected = new SortedLists(sortedByRate,sortedByComment,"target");
        final SortedLists sortedLists = new SortedLists(shuffledRate,shuffledComment,"target");
        when(sortedListRepo.count()).thenReturn(Long.valueOf(1));
        when(sortedListRepo.findAll()).thenReturn(new ArrayList(Arrays.asList(sortedLists)));
        when(sortedListRepo.save(sortedLists)).thenReturn(sortedLists);
        final SortedLists returned = sortService.sort("comment");
        assertEquals(expected.getSortedByComment(),returned.getSortedByComment());
    }
    @Test
    void shouldAddBookToSortedLists(){
        final Genre journal = new Genre("5asd25dfgf","Journal");
        final Book book0 = new Book("ash2jhs45",Integer.valueOf("456"),journal,"...","Günlükler","Ivan Gonçarov","45afs34",true,"1234567891234");
        final Book book1 = new Book("ash2jhs25",Integer.valueOf("141"),journal,".....","Günlükler","Edmondo de Amicis","44afs34",true,"1254566891234");
        final Book book2 = new Book("ash2sdhs44",Integer.valueOf("360"),journal,".....","Günlükler","Sylvia Plath","45afs84",false,"1234567891129");
        final List<Book> sortedByRate = new ArrayList(Arrays.asList(book1,book2));
        final List<Book> sortedByComment = new ArrayList(Arrays.asList(book1,book2));
        final SortedLists sortedLists = new SortedLists(sortedByRate,sortedByComment,"target");
        doReturn(sortedLists).when(sortServiceSpy).findOne();
        when(sortedListRepo.count()).thenReturn(Long.valueOf(1));
        when(sortedListRepo.findAll()).thenReturn(new ArrayList(Arrays.asList(sortedLists)));
        when(sortedListRepo.save(sortedLists)).thenReturn(sortedLists);
        final SortedLists expected = sortService.add(book0);
        assertTrue(expected.getSortedByComment().contains(book0));
        assertTrue(expected.getSortedByRate().contains(book0));

    }

}
