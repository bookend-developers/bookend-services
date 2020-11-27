package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.ShelfRequest;
import com.bookend.shelfservice.repository.ShelfRepository;
import com.bookend.shelfservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelfServiceImpl implements ShelfService {

    private ShelfRepository shelfRepository;
    private TagRepository tagRepository;
    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }



    @Autowired
    public void setShelfRepository(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @Autowired
    public void setBookRepository(ShelfRepository shelfRepository){
        this.shelfRepository=shelfRepository;
    }

    @Override
    public Shelf getById(Long id) {
        return  shelfRepository.findShelfById(id);
    }

    @Override
    public Shelf saveOrUpdate(ShelfRequest shelfRequest, String username) {
        List<Shelf> shelves = shelfRepository.findShelvesByUsername(username);
        if (!shelves.isEmpty()) {
            if (shelves.stream().anyMatch(s -> s.getShelfname().toLowerCase().matches(shelfRequest.getShelfname().toLowerCase()))) {
                return null;
            }
                if (shelfRequest.getTags() == null || shelfRequest.getTags().isEmpty()) {
                    return shelfRepository.save(new Shelf(shelfRequest.getShelfname(),username));
                }
                List<Tag> tags = shelfRequest.getTags()
                        .stream()
                        .map(t -> tagRepository.findByTag(t))
                        .collect(Collectors.toList());
                return shelfRepository.save(new Shelf(shelfRequest.getShelfname(), username, tags));
            }
        return null;
        }


    @Override
    public List<Shelf> findShelvesByUsername(String username) {

        return shelfRepository.findShelvesByUsername(username);
    }

    @Override
    public void deleteShelf(Shelf shelf) {
         shelfRepository.delete(shelf);
    }

    @Override
    public List<String> getBooks(Long id) {
        List<String> bookIDs = getById(id).getShelfsBooks()
                                            .stream()
                                            .map(ShelfsBook::getBookID)
                                            .collect(Collectors.toList());

        return bookIDs;
    }
}
