package org.example.microservices.micro_file.service;

import org.example.microservices.micro_file.repository.FileRepository;
import org.example.microservices.model.DTO.FileInfoDTO;
import org.example.microservices.model.entity.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File storeFile(MultipartFile file, String role) throws IOException {
        File fileEntity = new File();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setType(file.getContentType());
        fileEntity.setData(file.getBytes());
        fileEntity.setRole(role);
        return fileRepository.save(fileEntity);
    }

    public Optional<File> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public List<FileInfoDTO> getAllFilesInfo() {
        return fileRepository.findAll()
                .stream()
                .map(file -> new FileInfoDTO(file.getId(), file.getName(), file.getType()))
                .collect(Collectors.toList());
    }

    public List<FileInfoDTO> getAllFilesInfoWithRole(String role) {
        if (role.equals("admin")) {
            return fileRepository.findAll()
                    .stream()
                    .map(file -> new FileInfoDTO(file.getId(), file.getName(), file.getType()))
                    .collect(Collectors.toList());
        } else if (role.equals("pro")) {
            return fileRepository.findAll()
                    .stream()
                    .filter(file -> file.getRole().equals("pro") || file.getRole().equals("default"))
                    .map(file -> new FileInfoDTO(file.getId(), file.getName(), file.getType()))
                    .collect(Collectors.toList());
        }
        return fileRepository.findAll()
                .stream()
                .filter(file -> file.getRole().equals("default"))
                .map(file -> new FileInfoDTO(file.getId(), file.getName(), file.getType()))
                .collect(Collectors.toList());
    }
}

