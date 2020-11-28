package com.bookend.shelfservice.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name="tags")
public class Tag {
    @javax.persistence.Id
    @Id
    private String id;
    private String tag;
    @ManyToMany(mappedBy = "tags")
    private List<Shelf> shelfs;

    public Tag(String tag) {
        this.tag = tag;
    }

    public Tag(String id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public Tag() {
    }
    @JsonGetter
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }
}
