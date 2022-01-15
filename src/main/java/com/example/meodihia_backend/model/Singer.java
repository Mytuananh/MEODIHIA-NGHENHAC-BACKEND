package com.example.meodihia_backend.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Singer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String countryside;
    private String avatar;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "singer", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Song> songList;

}
