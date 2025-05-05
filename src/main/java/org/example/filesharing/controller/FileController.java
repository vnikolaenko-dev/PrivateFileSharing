package org.example.filesharing.controller;

import org.example.filesharing.model.DTO.FileInfoDTO;
import org.example.filesharing.model.entity.File;
import org.example.filesharing.model.entity.User;
import org.example.filesharing.service.FileService;
import org.example.filesharing.util.JwtTokenUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    private final JwtTokenUtils jwtTokenUtils;
    private final FileService fileService;

    public FileController(JwtTokenUtils jwtTokenUtils, FileService fileService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.fileService = fileService;
    }

    @PostMapping("/upload/{role}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String role) {
        try {
            File savedFile = fileService.storeFile(file, role);
            return "Файл загружен с ID: " + savedFile.getId();
        } catch (Exception e) {
            return "Ошибка при загрузке файла: " + e.getMessage();
        }
    }

    @GetMapping
    public List<FileInfoDTO> listAllFiles(@RequestHeader("Authorization") String authHeader) {
        String role = jwtTokenUtils.getUserRoleFromToken(authHeader.replace("Bearer ", ""));
        return fileService.getAllFilesInfoWithRole(role);
    }


    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) {
        return fileService.getFile(id)
                .map(file -> {
                    String filename = file.getName();
                    String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                            .replaceAll("\\+", "%20"); // заменяем "+" на "%20" для пробелов

                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType(file.getType()));
                    headers.setContentLength(file.getData().length);
                    headers.set(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename*=UTF-8''" + encodedFilename);

                    return new ResponseEntity<>(file.getData(), headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
