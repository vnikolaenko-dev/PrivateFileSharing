package org.example.filesharing.controller;

import lombok.RequiredArgsConstructor;
import org.example.filesharing.kafka.KafkaProducer;
import org.example.filesharing.model.entity.User;
import org.example.filesharing.model.response.JwtResponse;
import org.example.filesharing.security.DatabaseCrypto;
import org.example.filesharing.service.UserService;
import org.example.filesharing.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KafkaProducer kafkaProducer;

    @PostMapping("/create")
    public void create(@RequestBody User user) {
        System.out.println(user);
        userService.save(user);
        kafkaProducer.sendToUser(user.getEmail(), "Welcome!\n" +
                "User with your email created in PrivateSharingFile.\n" +
                "Your password is: " + user.getPassword()
        );
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getUserRepository().findAll();
    }
}
