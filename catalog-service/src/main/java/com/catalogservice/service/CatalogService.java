package com.catalogservice.service;

import com.catalogservice.model.CatalogItem;

import java.util.List;

public interface CatalogService {
    public List<CatalogItem> getBooks(Long userID,String accessToken);
}
