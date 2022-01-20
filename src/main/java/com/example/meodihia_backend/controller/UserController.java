package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.song.SongService;
import com.example.meodihia_backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserDetailServices userDetailServices;
    @Autowired
    SongService songService;

    @GetMapping("list-user")
    public ResponseEntity<?> pageUser(@PageableDefault(sort = "username", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userPage = userService.findAll(pageable);
        if (userPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }
    @PostMapping("create-song-user")
    public  ResponseEntity<?> createSongUser(@RequestBody Song song){
        User user = userService.findById(song.getUser().getId()).get();
        Song  song1= new Song();
        song1.setName(song.getName());
        song1.setDescription(song.getDescription());
        song1.setFile(song.getFile());
        song1.setAvatar(song.getAvatar());
        song1.setSingers(song.getSingers());
        song1.setMusician(song.getMusician());
        song1.setCount(0L);
        song1.setCountLike(0L);
        songService.save(song1);
        return new ResponseEntity<>(new ResponeMessage("yes"), HttpStatus.CREATED);
    }


}