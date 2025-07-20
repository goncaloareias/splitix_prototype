package pt.isep.splitix.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.domain.Notification;
import pt.isep.splitix.domain.Enum.NotificationType;
import pt.isep.splitix.repository.UserRepository;
import pt.isep.splitix.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;
    private final NotificationRepository notificationRepo;

    public UserService(UserRepository repo, NotificationRepository notificationRepo) {
        this.repo = repo;
        this.notificationRepo = notificationRepo;
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

    public boolean addFriend(String userEmail, String friendEmail) {
        Optional<User> userOpt = repo.findByEmail(userEmail);
        Optional<User> friendOpt = repo.findByEmail(friendEmail);

        if (userOpt.isEmpty() || friendOpt.isEmpty()) return false;

        User user = userOpt.get();
        User friend = friendOpt.get();

        if (user.equals(friend) || user.getFriends().contains(friend)) return false;

        // Relacionamento bidirecional
        user.getFriends().add(friend);
        friend.getFriends().add(user);

        repo.save(user);
        repo.save(friend); // <- Isto é importante para manter consistência!

        // Criação da notificação de amizade
        Notification notification = new Notification(
                user.getFname() + " quer ser teu amigo!",
                NotificationType.FRIEND_REQUEST,
                friend
        );
        notificationRepo.save(notification);

        return true;
    }


    public User getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Utilizador não autenticado.");
        }
        return user;
    }

}
