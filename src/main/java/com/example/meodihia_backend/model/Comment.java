package com.example.meodihia_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;


@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "song_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Song song;

    @ManyToOne
    @JoinColumn(name = "playlist_id")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Playlist playlist;
}