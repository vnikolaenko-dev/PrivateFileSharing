package org.example.microservices.micro_user.service;

import lombok.Data;
import org.example.microservices.model.entity.User;
import org.example.microservices.micro_user.repository.UserRepository;
import org.example.microservices.security.DatabaseCrypto;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {
    private final DatabaseCrypto databaseCrypto;
    private final UserRepository userRepository;

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

    public void save(User user) {
        user.setPassword(databaseCrypto.encrypt(user.getPassword()));
        userRepository.save(user);
    }

    public void deleteByEmail(String email) {
        System.out.println("Attempting to delete user with email: " + email);
        userRepository.deleteUserByEmail(email);
        System.out.println("Deletion completed for email: " + email);
    }
}
