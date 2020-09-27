package com.catalogservice.controller;

import com.catalogservice.model.Book;
import com.catalogservice.model.CatalogItem;
import com.catalogservice.model.Shelf;
import com.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {
    private CatalogService catalogService;
    @Autowired
    public void setCatalogService(CatalogService catalogService) {
        this.catalogService = catalogService;
    }


    @GetMapping("user/book/{userid}")
    public List<CatalogItem> getCatalog(@PathVariable("userid") String userID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        //token
        String accessToken = details.getTokenValue();
        return catalogService.getBooks(Long.valueOf(userID),accessToken);
    }
    @GetMapping("user/{userid}/shelves")
    public List<Shelf> getUserShelves(@PathVariable("userid") String userID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        //token
        String accessToken = details.getTokenValue();
        return catalogService.getUserShelves(Long.valueOf(userID),accessToken);
    }
    @GetMapping("book/{bookid}")
    public Book getBook(@PathVariable("bookid") String bookID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        //token

        String accessToken = details.getTokenValue();
        return catalogService.getBook(Long.valueOf(bookID),accessToken);
    }
    @GetMapping("user/shelf/{shelfid}")
    public List<Book> getBooksofShelf(@PathVariable("shelfid") String shelfID, OAuth2Authentication auth){
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        //token
        String accessToken = details.getTokenValue();
        return catalogService.getBooksofShelf(Long.valueOf(shelfID),accessToken);
    }
}
