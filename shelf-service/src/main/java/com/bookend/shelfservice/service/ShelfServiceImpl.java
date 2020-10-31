package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelfServiceImpl implements ShelfService {

    private ShelfRepository shelfRepository;
    @Autowired
    public void setBookRepository(ShelfRepository shelfRepository){
        this.shelfRepository=shelfRepository;
    }

    @Override
    public Shelf getById(Long id) {
        return  shelfRepository.findShelfById(id);
    }



    @Override
    public Shelf saveOrUpdate(Shelf shelf) {
        List<Shelf> shelves = shelfRepository.findShelvesByUsername(shelf.getUsername());

        if(shelves.stream().anyMatch(s -> s.getShelfname().toLowerCase().matches(shelf.getShelfname().toLowerCase()))){
            return null;
        }
        return shelfRepository.save(shelf);
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
