package com.accountservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name= "shelf")
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shelfId")
    private Long id;
    private String shelfname;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;
    @OneToMany(mappedBy = "shelf")
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
