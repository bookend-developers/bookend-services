package com.bookend.bookservice.service;

import com.bookend.bookservice.model.Book;
import com.bookend.bookservice.model.SortedLists;
import com.bookend.bookservice.repository.SortedListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    private SortedListRepo sortedListRepo;


    @Autowired
    public void setSortedListRepo(SortedListRepo sortedListRepo) {
        this.sortedListRepo = sortedListRepo;
    }


    @Override
    public SortedLists add(Book book) {
        SortedLists sortedLists = findOne();
        sortedLists = sortedListRepo.save(sortedLists.add(book));
        return sortedLists;

    }


    @Override
    public SortedLists sort(String type) {
        SortedLists sortedLists = findOne();
        sortedLists.sortByType(type);
        sortedLists = sortedListRepo.save(sortedLists);
        return sortedLists;
    }

    @Override
    public SortedLists findOne() {

        if(sortedListRepo.count()>1){
            return  sortedListRepo.save(new SortedLists("target"));
        }

        return  sortedListRepo.findAll().get(0);
    }

    @Override
    public SortedLists remove(Book book) {
        SortedLists sortedLists=findOne();
        sortedLists = sortedLists.remove(book);
        return sortedListRepo.save(sortedLists) ;
    }
}
