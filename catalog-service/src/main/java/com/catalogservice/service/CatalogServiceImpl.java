package com.catalogservice.service;

import com.catalogservice.model.Book;
import com.catalogservice.model.CatalogItem;
import com.catalogservice.model.Rate;

import com.catalogservice.model.Shelf;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Override
    public List<CatalogItem> getBooks(String username,String accessToken) {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<Rate[]> responseEntity =restTemplate.exchange("http://localhost:8081/rate/user/{userId}", HttpMethod.GET, entity, Rate[].class,username);


        List<Rate> ratelist = Arrays.asList(responseEntity.getBody());
        List<CatalogItem> items = new ArrayList<CatalogItem>();

        for(Rate rate : ratelist){
            CatalogItem item = new CatalogItem();
            item.setRating(rate.getRate());
            //TODO check if null
            Book book =restTemplate.exchange("http://localhost:8082/book/{bookId}", HttpMethod.GET, entity,Book.class,rate.getBookId()).getBody();
            item.setBookName(book.getBookName());
            item.setDescription(book.getDescription());
            item.setAuthor(book.getAuthor());
            item.setGenre(book.getGenre());
            items.add(item);
        }
        return items;
    }

    @Override
    public List<Shelf> getUserShelves(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<Shelf[]> responseEntity =restTemplate.exchange("http://localhost:8083/shelves", HttpMethod.GET, entity, Shelf[].class);

        return Arrays.asList(responseEntity.getBody());
    }

    @Override
    public Book getBook(String bookID, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        return restTemplate.exchange("http://localhost:8082/book/{bookId}", HttpMethod.GET, entity,Book.class,bookID).getBody();

    }

    @Override
    public List<Book> getBooksofShelf(Long shelfID, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        ResponseEntity<String[]> responseEntity =restTemplate.exchange("http://localhost:8083/shelf/{shelfid}", HttpMethod.GET, entity, String[].class,shelfID);
        List<String> bookIDs = Arrays.asList(responseEntity.getBody());
        List<Book> books = bookIDs.stream()
                                        .map(id ->restTemplate.exchange("http://localhost:8082/book/{bookId}", HttpMethod.GET, entity,Book.class,id).getBody())
                                        .collect(Collectors.toList());
        return books;
    }

    @Override
    public Book addBookstoShelf(Long shelfID, String bookID, String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        Map<String, String> params = new HashMap<>();
        params.put("shelfid", shelfID.toString());
        params.put("bookid", bookID);
        Book addedBook=  restTemplate.exchange("http://localhost:8083/{shelfid}/{bookid}"
                , HttpMethod.POST,
                entity,Book.class,params).getBody();
        //TODO make it catalog item
        return restTemplate.exchange("http://localhost:8082/book/{bookId}", HttpMethod.GET, entity,Book.class,bookID).getBody();

    }

}
