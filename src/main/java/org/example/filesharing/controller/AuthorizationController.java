package org.example.filesharing.controller;

import lombok.RequiredArgsConstructor;
import org.example.filesharing.model.entity.User;
import org.example.filesharing.model.response.JwtResponse;
import org.example.filesharing.security.DatabaseCrypto;
import org.example.filesharing.service.UserService;
import org.example.filesharing.util.JwtTokenUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthorizationController {
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final DatabaseCrypto crypto;

    /**
     * Обрабатывает запрос на вход пользователя.
     *
     * @param user Объект пользователя с email и паролем
     * @return JwtResponse с токеном или сообщением об ошибке
     */
    @PostMapping("/login")
    public JwtResponse login(@RequestBody User user) {
        User userFromDB = userService.findUserByEmail(user.getEmail());
        // Проверяем существование пользователя и совпадение паролей
        if (userFromDB != null && crypto.decrypt(userFromDB.getPassword()).equals(String.valueOf(user.getPassword()))) {
            return new JwtResponse(jwtTokenUtils.generateToken(user.getEmail(), userFromDB.getRole()), userFromDB.getRole());
        } else {
            return new JwtResponse(null, null);
        }
    }
}
