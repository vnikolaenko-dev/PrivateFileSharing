package org.example.filesharing.model.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileInfoDTO {
    private Long id;
    private String name;
    private String type;
    private String accessLevel = "public";
    private LocalDateTime uploadDate = LocalDateTime.now();

    public FileInfoDTO(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // геттеры и сеттеры
}

