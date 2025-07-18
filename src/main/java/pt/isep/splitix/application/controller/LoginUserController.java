package pt.isep.splitix.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginUserController {

    private final UserService userService;

    public Optional<User> login(String email, String password) {
        return userService.login(email, password);
    }
}

