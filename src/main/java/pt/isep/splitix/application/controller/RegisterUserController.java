package pt.isep.splitix.application.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

@Controller
@RequiredArgsConstructor
public class RegisterUserController {

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";  // nome do template Thymeleaf
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String fname,
                               @RequestParam String lname,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "As passwords não coincidem.");
            return "register";
        }

        try {
            User user = new User();
            user.setFname(fname);
            user.setLname(lname);
            user.setEmail(email);
            user.setPassword(password);

            userService.register(user);

            return "success";  // página de sucesso (pode ser "login" para redirecionar ao login)
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
