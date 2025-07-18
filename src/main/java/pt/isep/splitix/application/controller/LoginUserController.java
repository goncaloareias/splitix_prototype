package pt.isep.splitix.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.UserRepository;
import pt.isep.splitix.service.UserService;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
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

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        Optional<User> userOpt = userService.login(email, password);

        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "success";  // página de sucesso
        } else {
            model.addAttribute("error", "Credenciais inválidas.");
            return "login";
        }
    }

    @GetMapping("/reset-password")
    public String resetForm() {
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetSubmit(@RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String confirmPassword,
                              Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "As passwords não coincidem.");
            return "reset-password";
        }

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "Email não registado.");
            return "reset-password";
        }

        userService.changePasswordWithoutOldPassword(email, password);
        return "success";
    }
}

