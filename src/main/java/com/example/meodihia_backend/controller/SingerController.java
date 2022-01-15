package com.example.meodihia_backend.controller;

import com.example.meodihia_backend.dto.response.ResponeMessage;
import com.example.meodihia_backend.model.Singer;
import com.example.meodihia_backend.model.Song;
import com.example.meodihia_backend.model.User;
import com.example.meodihia_backend.security.userprincal.UserDetailServices;
import com.example.meodihia_backend.service.comment.CommentService;
import com.example.meodihia_backend.service.singer.SingerService;
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
public class SingerController {
    @Autowired
    UserDetailServices userDetailServices;
    @Autowired
    SingerService singerService;

    @GetMapping("/list-singer")
    public ResponseEntity<?> pageSinger(@PageableDefault(sort = "name", direction = Sort.Direction.ASC) Pageable pageable){
        Page<Singer> singerPage = singerService.findAll(pageable);
        if(singerPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(singerPage, HttpStatus.OK);
    }

    @PostMapping("/create-singer")
    public ResponseEntity<?> createSong(@RequestBody Singer singer){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        singerService.save(singer);
        return new ResponseEntity<>(singer, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-singer/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        singerService.deleteById(id);
        return new ResponseEntity<>(new ResponeMessage("Done Delete!"),HttpStatus.OK);
    }

    @GetMapping("/find-singer/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return new ResponseEntity<>(singerService.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("/edit-singer/{id}")
    public ResponseEntity<?> editSong(@RequestBody Singer singer, @PathVariable Long id){
        User user = userDetailServices.getCurrentUser();
        if(user.getUsername().equals("Anonymous")){
            return new ResponseEntity<>(new ResponeMessage("Please login!"), HttpStatus.OK);
        }
        Singer singer1 = singerService.findById(id).get();
        singer1.setName(singer.getName());
        singer1.setAge(singer.getAge());
        singer1.setCountryside(singer.getCountryside());
        singer1.setSongList(singer.getSongList());
        singerService.save(singer1);
        return new ResponseEntity<>(new ResponeMessage("Done Edit!"), HttpStatus.OK);
    }

    @GetMapping("/find-singer-by-name/{name}")
    public ResponseEntity<?> findSongByName(@PathVariable("name") String name){
        Pageable pageable = PageRequest.of(0,10,Sort.by("id").descending());
        Page<Singer> singerPage = singerService.findSingerByNameContaining(name,pageable);
        List<Singer> list = singerPage.getContent();
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
