package com.bookclupservice.bookclubservice.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clubName;

    private String description;

    @ManyToOne
    private Member owner;

    @OneToMany(mappedBy = "club")
    private List<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "club_member",
            joinColumns = @JoinColumn(name = "club_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Member> members;

    @ManyToMany
    @JoinTable(
            name = "post_club_member",
            joinColumns = @JoinColumn(name = "post_club_id"),
            inverseJoinColumns = @JoinColumn(name = "post_member_id"))
    private List<Member> postMembers;

    private boolean isPrivate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public List<Member> getPostMembers() {
        return postMembers;
    }

    public void setPostMembers(List<Member> postMembers) {
        this.postMembers = postMembers;
    }

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }
}
