package com.bookend.bookservice.service;

import com.bookend.bookservice.repository.BookListByCommentRepo;
import com.bookend.bookservice.repository.BookListByRateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl implements SortService {
    private BookListByRateRepo bookListByRateRepo;
    private BookListByCommentRepo bookListByCommentRepo;
    @Autowired
    public void setBookListByCommentRepo(BookListByCommentRepo bookListByCommentRepo) {
        this.bookListByCommentRepo = bookListByCommentRepo;
    }
    @Autowired
    public void setBookListByRateRepo(BookListByRateRepo bookListByRateRepo) {
        this.bookListByRateRepo = bookListByRateRepo;
    }
}
