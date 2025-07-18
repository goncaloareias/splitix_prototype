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
    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void changePasswordWithoutOldPassword(String email, String newPassword) {
        Optional<User> userOpt = repo.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilizador não encontrado.");
        }

        if (newPassword.length() < 6) {
            throw new RuntimeException("A nova password deve ter pelo menos 6 caracteres.");
        }

        User user = userOpt.get();
        user.setPassword(newPassword);
        repo.save(user);
    }

    public User updateProfile(User currentUser, User updatedData) {
        currentUser.setFname(updatedData.getFname());
        currentUser.setLname(updatedData.getLname());
        currentUser.setEmail(updatedData.getEmail());
        return repo.save(currentUser);
    }
}
