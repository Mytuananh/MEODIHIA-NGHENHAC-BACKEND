package com.example.meodihia_backend.service.song;

import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISongService extends IGeneralService<Song> {
    Page<Song> findSongByNameContaining(String name,Pageable pageable);
    boolean existsByName(String name);
    Page<Song> findAllLaters(Pageable pageable);
<<<<<<< HEAD
    Page<Song> findAllByCount(Pageable pageable);
=======
    Iterable<Song> findAllByUser_Id(Long id);

>>>>>>> 4073d3f (pull dev)

}
