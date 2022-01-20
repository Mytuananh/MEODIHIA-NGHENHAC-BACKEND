package com.example.meodihia_backend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String file;
    private String avatar;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "song_singer", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "singer_id"))
    private List<Singer> singers;

    private String musician;
    private Long count;
    private Long countLike;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private User user;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Likes> likesList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "song", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> commentList;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Playlist.class)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Playlist> playlists;

    public Song() {
    }

    public Song(Long id, String name, String description, String file, String avatar, List<Singer> singers, String musician, Long count, Long countLike, User user, List<Likes> likesList, List<Comment> commentList, List<Playlist> playlists) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.file = file;
        this.avatar = avatar;
        this.singers = singers;
        this.musician = musician;
        this.count = count;
        this.countLike = countLike;
        this.user = user;
        this.likesList = likesList;
        this.commentList = commentList;
        this.playlists = playlists;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<Singer> getSingers() {
        return singers;
    }

    public void setSingers(List<Singer> singers) {
        this.singers = singers;
    }

    public String getMusician() {
        return musician;
    }

    public void setMusician(String musician) {
        this.musician = musician;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCountLike() {
        return countLike;
    }

    public void setCountLike(Long countLike) {
        this.countLike = countLike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Likes> getLikesList() {
        return likesList;
    }

    public void setLikesList(List<Likes> likesList) {
        this.likesList = likesList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }
}