package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.exception.TagNotFound;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.GenreMessage;
import com.bookend.shelfservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private TagRepository tagRepository;
    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }



    @Override
    public List<Tag> allTag() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findByID(String id) throws TagNotFound {
        Tag tag = tagRepository.findTagById(id);
        if(tag == null) {
            throw new TagNotFound("Tag does not exist.");
        }
        return tag;
    }

    @Override
    public Tag save(GenreMessage newGenre) throws AlreadyExists {
        if(tagRepository.findByTag(newGenre.getGenre())!=null){
            throw new AlreadyExists("Tag name already exist");
        }
        Tag checkTag = tagRepository.findTagById(newGenre.getId());
        if(checkTag!=null){
            checkTag.setTag(newGenre.getGenre());
            return tagRepository.save(checkTag);
        }
        else {
            return tagRepository.save(new Tag(newGenre.getId(),newGenre.getGenre()));
        }

    }
}
