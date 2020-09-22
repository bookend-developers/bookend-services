package com.catalogservice.service;

import com.catalogservice.model.Book;
import com.catalogservice.model.CatalogItem;
import com.catalogservice.model.Rate;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    @Override
    public List<CatalogItem> getBooks(Long userID,String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        RestTemplate restTemplateRate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+accessToken);
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        ResponseEntity<Rate[]> responseEntity =restTemplate.exchange("http://localhost:8081/rate/user/{userId}", HttpMethod.GET, entity, Rate[].class,userID);


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

}
