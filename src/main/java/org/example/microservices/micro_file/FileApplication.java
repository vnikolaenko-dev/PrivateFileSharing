package org.example.microservices.micro_file;

import org.example.notificator.NotificatorApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Map;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.microservices.micro_file.repository")
@EntityScan(basePackages = "org.example.microservices.model.entity")
@ComponentScan(basePackages = {
        "org.example.microservices.micro_file",
        "org.example.microservices.config",
        "org.example.microservices.security",
        "org.example.microservices.util"
})
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FileApplication.class);
        app.setDefaultProperties(Map.of("server.port", "8082"));
        app.run(args);
    }
}
