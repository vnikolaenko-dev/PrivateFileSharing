package org.example.filesharing.repository;


import org.example.filesharing.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

