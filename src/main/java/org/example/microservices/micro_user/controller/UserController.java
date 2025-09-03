package org.example.microservices.micro_user.controller;

import lombok.RequiredArgsConstructor;
import org.example.microservices.micro_user.kafka.KafkaProducer;
import org.example.microservices.model.entity.User;
import org.example.microservices.micro_user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @GetMapping("/get-all")
    public List<User> getAll() {
        return userService.getUserRepository().findAll();
    }

    @PostMapping("/create")
    public void create(@RequestBody User user) {
        userService.save(user);
        kafkaProducer.sendToUser(user.getEmail(), "Welcome!\n" +
                "User with your email created in PrivateSharingFile.\n" +
                "Your password is: " + user.getPassword()
        );
    }

    @DeleteMapping("/remove/{email}")
    public ResponseEntity<String> remove(@PathVariable String email) {
        System.out.println(email);
        try {
            User user = userService.findUserByEmail(email);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User with email " + email + " not found");
            }

            userService.deleteByEmail(email);
            kafkaProducer.sendToUser(email, "Your account has been deleted from PrivateSharingFile.");

            return ResponseEntity.ok("User with email " + email + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }
}
