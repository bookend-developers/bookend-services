package com.bookend.shelfservice.service;

import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return shelfRepository.save(shelf);
    }

    @Override
    public List<Shelf> findShelvesByUsername(String username) {

        return shelfRepository.findShelvesByUsername(username);
    }
}
