package org.example.filesharing.service;

import lombok.Data;
import org.example.filesharing.model.entity.User;
import org.example.filesharing.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserService {
    private final UserRepository userRepository;

    public User findUserByLogin(String login){
        return userRepository.findUserByLogin(login);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
