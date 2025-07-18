package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public String loginForm(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> userOpt = userService.login(email, password);

        if (userOpt.isPresent()) {
            session.setAttribute("user", userOpt.get());  // Guarda user na sessão
            return "redirect:/dashboard";  // Redireciona para dashboard
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
