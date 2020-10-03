package com.catalogservice.service;

import com.catalogservice.model.Book;
import com.catalogservice.model.CatalogItem;
import com.catalogservice.model.Shelf;

import java.util.List;

public interface CatalogService {
    public List<CatalogItem> getBooks(String username,String accessToken);
    public List<Shelf> getUserShelves(String accessToken);
    public Book getBook(String bookID,String accessToken);
    //TODO make it full book data(catalog item)
    public List<Book> getBooksofShelf(Long shelfID,String accessToken);

    Book addBookstoShelf(Long shelfID, String bookID, String accessToken);
}
