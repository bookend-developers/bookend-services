package com.bookend.bookservice.serviceTest;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.repository.GenreRepository;
import com.bookend.bookservice.service.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GenreServiceTest {
    @Spy
    private GenreServiceImpl genreServiceSpy;
    @Mock
    private GenreRepository genreRepository;
    @Mock
    private Producer producer;
    @InjectMocks
    private GenreServiceImpl genreService;

    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldSaveNewGenreIfGenreDoesNotExistAlready() throws AlreadyExist, MandatoryFieldException {
        final Genre genre = new Genre("5asd25dfgf","Journal");
        doReturn(null).when(genreServiceSpy).findByGenre(genre.getGenre());
        when(genreRepository.findByGenre(genre.getGenre())).thenReturn(null);
        when(genreRepository.save(genre)).thenReturn(genre);
        final Genre expected = genreService.addNewGenre(genre.getGenre());

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToSaveNewGenreIfGenreAlreadyExist(){
        final Genre genre = new Genre("5asd25dfgf","Journal");
        doReturn(genre).when(genreServiceSpy).findByGenre(genre.getGenre());
        when(genreRepository.findByGenre(genre.getGenre())).thenReturn(genre);
        assertThrows(AlreadyExist.class,()->{
            genreService.addNewGenre(genre.getGenre());
        });
        verify(genreRepository,never()).save(any(Genre.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToSAveNewGenreIfGenreFieldEmptyString(){
        assertThrows(MandatoryFieldException.class,()->{
            genreService.addNewGenre("");
        });
        verify(genreRepository,never()).save(any(Genre.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToSAveNewGenreIfGenreFieldNull(){
        assertThrows(MandatoryFieldException.class,()->{
            genreService.addNewGenre(null);
        });
        verify(genreRepository,never()).save(any(Genre.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFindGenreIfGenreExistWithGivenID() throws NotFoundException {
        final Genre genre = new Genre("5asd25dfgf","Journal");
        when(genreRepository.findGenreById(genre.getId())).thenReturn(genre);
        final Genre expected = genreService.findById(genre.getId());
        assertThat(expected).isNotNull();
        assertEquals(expected,genre);

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToFindGenreIfNoGenreExistWithGivenID(){
        final Genre genre = new Genre("5asd25dfgf","Journal");
        when(genreRepository.findGenreById(genre.getId())).thenReturn(null);
        assertThrows(NotFoundException.class,()->{
            genreService.findById(genre.getId());
        });
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldUpdateGenre() throws NotFoundException, AlreadyExist, MandatoryFieldException {
        final Genre oldGenre = new Genre("5asd25dfgf","poem");
        final Genre newGenre = new Genre("5asd25dfgf","Poem");
        doReturn(oldGenre).when(genreServiceSpy).findById(oldGenre.getId());
        doReturn(null).when(genreServiceSpy).findByGenre(newGenre.getGenre());
        when(genreRepository.findByGenre(newGenre.getGenre())).thenReturn(null);
        when(genreRepository.findGenreById(oldGenre.getId())).thenReturn(oldGenre);
        when(genreRepository.save(oldGenre)).thenReturn(newGenre);
        final Genre expected = genreService.update(newGenre);
        assertThat(expected).isNotNull();
        assertEquals(expected.getGenre().toLowerCase(),newGenre.getGenre().toLowerCase());
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToUpdateGenreIfGenreNAmeAlreadyOccupied(){
        final Genre newGenre = new Genre("5asd25dfgf","Poem");
        doReturn(newGenre).when(genreServiceSpy).findByGenre(newGenre.getGenre());
        when(genreRepository.findByGenre(newGenre.getGenre())).thenReturn(newGenre);
        assertThrows(AlreadyExist.class,()->{
            genreService.update(newGenre);
        });
        verify(genreRepository,never()).save(any(Genre.class));

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToUpdateGenreIfGenreNAmeEmptyString() throws NotFoundException {
        final Genre oldGenre = new Genre("5asd25dfgf","poem");
        final Genre newGenre = new Genre("5asd25dfgf","");
        doReturn(oldGenre).when(genreServiceSpy).findById(oldGenre.getId());
        doReturn(null).when(genreServiceSpy).findByGenre(newGenre.getGenre());
        when(genreRepository.findByGenre(newGenre.getGenre())).thenReturn(null);
        when(genreRepository.findGenreById(oldGenre.getId())).thenReturn(oldGenre);
        assertThrows(MandatoryFieldException.class,()->{
            genreService.update(newGenre);
        });
        verify(genreRepository,never()).save(any(Genre.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToUpdateGenreIfGenreNAmeNull() throws NotFoundException {
        final Genre oldGenre = new Genre("5asd25dfgf","poem");
        final Genre newGenre = new Genre("5asd25dfgf",null);
        doReturn(oldGenre).when(genreServiceSpy).findById(oldGenre.getId());
        doReturn(null).when(genreServiceSpy).findByGenre(newGenre.getGenre());
        when(genreRepository.findByGenre(newGenre.getGenre())).thenReturn(null);
        when(genreRepository.findGenreById(oldGenre.getId())).thenReturn(oldGenre);
        assertThrows(MandatoryFieldException.class,()->{
            genreService.update(newGenre);
        });
        verify(genreRepository,never()).save(any(Genre.class));
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFailToUpdateGenreIfGenreDoesNotExists(){
        final Genre oldGenre = new Genre("5asd25dfgf","poem");
        final Genre newGenre = new Genre("5asd25dfgf","");
        try {
            doThrow(NotFoundException.class).when(genreServiceSpy).findById(oldGenre.getId());
        } catch (NotFoundException e) {
            assertTrue(e instanceof NotFoundException);
            assertEquals(e.getMessage(),"No genre is match with given id");
        }
        doReturn(null).when(genreServiceSpy).findByGenre(newGenre.getGenre());
        when(genreRepository.findByGenre(newGenre.getGenre())).thenReturn(null);
        when(genreRepository.findGenreById(oldGenre.getId())).thenReturn(oldGenre);
        verify(genreRepository,never()).save(any(Genre.class));

    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldReturnAllGenres(){
        final Genre genre1 = new Genre("5asd25dfgf","Journal");
        final Genre genre2 = new Genre("5asd24dfgf","Science-Fiction");
        final Genre genre3 = new Genre("5asd26dfgf","Fiction");
        final Genre genre4 = new Genre("5asd27dfgf","Classics");
        final List<Genre> genres = Arrays.asList(genre1,genre2,genre3,genre4);
        when(genreRepository.findAll()).thenReturn(genres);
        final List<Genre> expected = genreService.findAll();
        assertThat(expected).isNotNull();
        assertEquals(expected,genres);
    }
    @MockitoSettings(strictness = Strictness.WARN)
    @Test
    void shouldFindGenreWithGivenGenreField(){
        final Genre genre = new Genre("5asd25dfgf","Journal");
        when(genreRepository.findByGenre(genre.getGenre())).thenReturn(genre);
        final Genre expected = genreService.findByGenre(genre.getGenre());
        assertThat(expected).isNotNull();
        assertEquals(genre,expected);
    }
}
