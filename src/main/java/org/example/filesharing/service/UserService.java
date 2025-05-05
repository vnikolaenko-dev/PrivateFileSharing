package org.example.filesharing.service;

import lombok.Data;
import org.example.filesharing.model.entity.User;
import org.example.filesharing.repository.UserRepository;
import org.example.filesharing.security.DatabaseCrypto;
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
}
