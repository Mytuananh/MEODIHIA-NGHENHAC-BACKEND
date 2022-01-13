package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepository extends JpaRepository<Song,Long>{
    Iterable<Song> findAllByUser_Id(Long id);
    Page<Song> findAll(Pageable pageable);
    Page<Song> findSongsByNameContaining(String name, Pageable pageable);

}
