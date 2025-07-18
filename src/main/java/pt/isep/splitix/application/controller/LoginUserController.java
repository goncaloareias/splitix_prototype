package pt.isep.splitix.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.UserRepository;
import pt.isep.splitix.service.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LoginUserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public Optional<User> login(String email, String password) {
        return userService.login(email, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void changePasswordWithoutOldPassword(String email, String newPassword) {
        userService.changePasswordWithoutOldPassword(email, newPassword);
    }
}

