package pt.isep.splitix.application.controller;

import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class RegisterUserController {

    private final UserService service;

    public RegisterUserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    @GetMapping
    public List<User> all() {
        return service.listUsers();
    }
}
