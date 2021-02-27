package com.bookend.shelfservice.serviceTest;

import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.exception.TagNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.repository.ShelfRepository;
import com.bookend.shelfservice.repository.TagRepository;
import com.bookend.shelfservice.service.ShelfServiceImpl;
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
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void getTagIfIdHaveMatchSuccesfully() throws TagNotFound {
        final String id = "abdhsdj21515";
        final Tag tag = new Tag(id,"Horror");
        given(tagRepository.findTagById(id)).willReturn(tag);
        final Tag expected = tagService.findByID(id);
        assertThat(expected).isNotNull();
        assertEquals(tag,expected);
    }

    @Test
    void failToGetShelfIfIdDoesNotMatch() throws TagNotFound {
        final String id = "abdhsdj21515";
        given(tagRepository.findTagById(id)).willReturn(null);
        assertThrows(TagNotFound.class,()->{
            tagService.findByID(id);
        });
    }
    @Test
    void shouldReturnAllTags(){
        List<Tag> tags = new ArrayList<>();
        final Tag tag = new Tag("dsfdsfd32","Horror");
        final Tag tag2 = new Tag("dsfdaks56","Thriller");
        tags.add(tag);
        tags.add(tag2);
        given(tagRepository.findAll()).willReturn(tags);
        final List<Tag> expected = tagService.allTag();
        assertEquals(tags,expected);
    }
    @Test
    void shouldSaveGivenTagSuccessfully(){
        final Tag tag = new Tag("dsfdsfd32","Horror");
        given(tagRepository.save(tag)).willReturn(tag);
        final Tag saved = tagService.save(tag);
        assertThat(saved).isNotNull();
        verify(tagRepository).save(any(Tag.class));
    }
    @Test
    void shouldSaveandUpdateGivenTagSuccessfullyIfDoesNotExist(){
        given(tagRepository.findByTag("Horror")).willReturn(null);
        final Tag saved = tagService.saveAndUpdate("Horror");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void shouldReturnNullIfTagThatWillBeSavedAlreadyExists(){
        final Tag tag = new Tag("dsfdsfd32","Horror");
        given(tagRepository.findByTag("Horror")).willReturn(tag);
        final Tag saved = tagService.saveAndUpdate("Horror");
        assertThat(saved).isNull();
    }


}
