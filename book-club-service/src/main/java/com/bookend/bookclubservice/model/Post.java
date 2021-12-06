package com.bookend.bookclubservice.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @ManyToOne
    @JoinColumn(name="club_id", nullable = false)
    @JsonBackReference
    private Club club;
    private LocalDateTime date;
    @ManyToOne
    private Member writer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Member getWriter() {
        return writer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setWriter(Member writer) {
        this.writer = writer;
    }

    public Post() {
        this.date= LocalDateTime.now();
    }
}
