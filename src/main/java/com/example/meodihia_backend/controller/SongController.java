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
        Song song1 = songService.findById(id).get();
        song1.setName(song.getName());
        song1.setDescription(song.getDescription());
        song1.setFile(song.getFile());
        song1.setSinger(song.getSinger());
        song1.setMusician(song.getMusician());
        songService.save(song1);
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
        Comment newComment = new Comment();
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        Song song =songService.findById(comment.getIdSong()).get();
        String text = comment.getText();
        newComment.setUser(user);
        newComment.setSong(song);
        newComment.setText(text);
        commentService.save(newComment);
        return new ResponseEntity<>(new ResponeMessage("Post comment successfull"),HttpStatus.OK);
    }
}

