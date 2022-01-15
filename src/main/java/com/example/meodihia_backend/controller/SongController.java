package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.dto.CommentDto;
import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Comment;
import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.comment.CommentService;
import com.example.meodihia_backend.service.song.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class SongController {
    @Autowired
    UserDetailServices userDetailServices;
    @Autowired
    SongService songService;
    @Autowired
    CommentService commentService;
    @GetMapping("/list-song")
    public ResponseEntity<?> pageSong(@PageableDefault(sort = "name", direction = Sort.Direction.ASC)Pageable pageable){
        Page<Song> songPage = songService.findAll(pageable);
        if(songPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @PostMapping("/create-song")
    public ResponseEntity<?> createSong(@RequestBody Song song){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        songService.save(song);
        return new ResponseEntity<>(song, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-song/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        songService.deleteById(id);
        return new ResponseEntity<>(new ResponeMessage("Done Delete!"),HttpStatus.OK);
    }

    @GetMapping("/find-song/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(songService.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("/edit-song/{id}")
    public ResponseEntity<?> editSong(@RequestBody Song song, @PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }

        Optional<Song> song1 = songService.findById(id);
        if (!song1.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (songService.existsByName(song.getName())) {
            if (!song.getDescription().equals(song1.get().getDescription())) {
                song1.get().setDescription(song.getDescription());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getSinger().equals(song1.get().getSinger())) {
                song1.get().setSinger(song.getSinger());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getMusician().equals(song1.get().getMusician())) {
                song1.get().setMusician(song.getMusician());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getFile().equals(song1.get().getFile())) {
                song1.get().setFile(song.getFile());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getPlaylists().equals(song1.get().getPlaylists())) {
                song1.get().setPlaylists(song.getPlaylists());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            if (!song.getAvatar().equals(song1.get().getAvatar())) {
                song1.get().setAvatar(song.getAvatar());
                songService.save(song1.get());
                return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ResponeMessage("no_name_song"), HttpStatus.OK);
        }
        song1.get().setName(song.getName());
        song1.get().setDescription(song.getDescription());
        song1.get().setFile(song.getFile());
        song1.get().setSinger(song.getSinger());
        song1.get().setMusician(song.getMusician());
        song1.get().setPlaylists(song.getPlaylists());
        song1.get().setAvatar(song.getAvatar());
        songService.save(song1.get());
        return new ResponseEntity<>(new ResponeMessage("Done Edit!"), HttpStatus.OK);
    }

    @GetMapping("/find-song-by-name/{name}")
    public ResponseEntity<?> findSongByName(@PathVariable("name") String name){
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<Song> songPage = songService.findSongByNameContaining(name,pageable);
        List<Song> list = songPage.getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PostMapping("/comment")
    public ResponseEntity<?>createCommentForSong(@RequestBody CommentDto comment){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        Comment newComment = new Comment();
        Song song =songService.findById(comment.getIdSong()).get();
        String text = comment.getText();
        newComment.setUser(user);
        newComment.setSong(song);
        newComment.setText(text);
        commentService.save(newComment);
        return new ResponseEntity<>(new ResponeMessage("Post comment successfully"),HttpStatus.OK);
    }

    @GetMapping("/show-song-by-count")
    public ResponseEntity<?> showSongByCount(@PageableDefault(sort = "count", direction = Sort.Direction.DESC)Pageable pageable){
        Page<Song> songPage = songService.findAll(pageable);
        if(songPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songPage, HttpStatus.OK);
    }

    @GetMapping("/song-list")
    public ResponseEntity<?> showListSongNew() {
        List<Song> songList = songService.findAll();
        if (songList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(songList, HttpStatus.OK);
    }


}

