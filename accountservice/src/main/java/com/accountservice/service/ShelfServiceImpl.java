package com.accountservice.service;

import com.accountservice.model.Book;
import com.accountservice.model.Shelf;
import com.accountservice.repository.ShelfRepository;
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
    public List<Shelf> findShelvesByAccountID(Long accountID) {
        List<Shelf> shelves= shelfRepository.findShelvesByAccount_Id(accountID);
        return shelfRepository.findShelvesByAccount_Id(accountID);
    }
}
