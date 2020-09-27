package com.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private Long id;
    private String shelfname;
    private Account account;
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public String getShelfname() {
        return shelfname;
    }

    public void setShelfname(String shelfname) {
        this.shelfname = shelfname;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Shelf() {
    }

    public Shelf(String shelfname, Account account) {
        this.shelfname = shelfname;
        this.account = account;
        this.books = new ArrayList<Book>();

    }
}
