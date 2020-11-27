package com.bookend.shelfservice.payload;

import com.bookend.shelfservice.model.Tag;

import java.util.List;

public class ShelfRequest {
    private String shelfname;
    private List<String > tags;

    public ShelfRequest(String shelfname, List<String> tags) {
        this.shelfname = shelfname;
        this.tags = tags;
    }

    public ShelfRequest() {
    }

    public String getShelfname() {
        return shelfname;
    }

    public void setShelfname(String shelfname) {
        this.shelfname = shelfname;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
