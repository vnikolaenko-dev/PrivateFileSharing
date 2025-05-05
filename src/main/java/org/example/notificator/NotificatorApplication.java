package org.example.notificator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class NotificatorApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NotificatorApplication.class);
        app.setDefaultProperties(Map.of("server.port", "9000"));
        app.run(args);
    }
}
