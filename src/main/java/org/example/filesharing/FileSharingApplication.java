package org.example.filesharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.filesharing.repository")
@EntityScan(basePackages = "org.example.filesharing.model.entity")
public class FileSharingApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSharingApplication.class, args);
    }

}
