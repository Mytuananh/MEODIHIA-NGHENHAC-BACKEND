package com.example.meodihia_backend.dto.dto;

import lombok.Data;

@Data
public class CommentDto {
    private String text;
    private Long idUser;
    private Long idSong;
}
