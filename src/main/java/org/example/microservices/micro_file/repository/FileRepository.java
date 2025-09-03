package org.example.microservices.micro_file.repository;


import org.example.microservices.model.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}

