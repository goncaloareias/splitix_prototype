package pt.isep.splitix.service;

import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User register(User user) {
        if (repo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        return repo.save(user);
    }

    public Optional<User> login(String email, String password) {
        return repo.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public List<User> listUsers() {
        return repo.findAll();
    }
}
