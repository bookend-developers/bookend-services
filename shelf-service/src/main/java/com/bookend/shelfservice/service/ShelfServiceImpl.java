package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.MandatoryFieldException;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.model.Tag;
import com.bookend.shelfservice.payload.ShelfRequest;
import com.bookend.shelfservice.repository.ShelfRepository;
import com.bookend.shelfservice.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Shelf getById(Long id) throws ShelfNotFound {
        Shelf shelf = shelfRepository.findShelfById(id);
        if(shelf == null) {
            throw new ShelfNotFound("Shelf does not exist.");
        }
        return shelf;
    }

    @Override
    public Shelf saveOrUpdate(ShelfRequest shelfRequest, String username) throws MandatoryFieldException, AlreadyExists {
        List<Shelf> shelves = shelfRepository.findShelvesByUsername(username);
        if (!shelves.isEmpty()) {
            if (shelves.stream().anyMatch(s -> s.getShelfname().equalsIgnoreCase(shelfRequest.getShelfname()))) {
                throw new AlreadyExists("Shelf already exists");
            }

        }
        if (shelfRequest.getTags() == null || shelfRequest.getTags().isEmpty()) {
            return shelfRepository.save(new Shelf(shelfRequest.getShelfname(),username));

        }
        if(shelfRequest.getShelfname()==null || shelfRequest.getShelfname() == ""){
            throw new MandatoryFieldException("Shelf name cannot be empty.");
        }
        List<Tag> tags = shelfRequest.getTags()
                .stream()
                .map(t -> tagRepository.findByTag(t))
                .collect(Collectors.toList());
        return shelfRepository.save(new Shelf(shelfRequest.getShelfname(), username, tags));
    }


    @Override
    public List<Shelf> findShelvesByUsername(String username) {

        return shelfRepository.findShelvesByUsername(username);
    }

    @Override
    public void deleteShelf(Shelf shelf) throws NotFoundException {
        if(shelfRepository.findShelfById(shelf.getId())==null){
            throw new NotFoundException("Shelf does not exist!");
        }
        shelfRepository.delete(shelf);

    }

    @Override

    public List<ShelfsBook> getBooks(Long id) throws ShelfNotFound{
        List<ShelfsBook> bookIDs = getById(id).getShelfsBooks();

        return bookIDs;
    }
}
