package pt.isep.splitix.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.FriendRequest;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.domain.Notification;
import pt.isep.splitix.domain.Enum.NotificationType;
import pt.isep.splitix.repository.FriendRequestRepository;
import pt.isep.splitix.repository.UserRepository;
import pt.isep.splitix.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepo;
    private final FriendRequestRepository friendRequestRepository;
    private final NotificationRepository notificationRepository;

    public UserService(UserRepository repo, NotificationRepository notificationRepo, FriendRequestRepository friendRequestRepository, NotificationRepository notificationRepository) {
        this.userRepository= repo;
        this.notificationRepo = notificationRepo;
        this.friendRequestRepository = friendRequestRepository;
        this.notificationRepository = notificationRepository;
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean sendFriendRequest(String senderEmail, String receiverEmail) {
        Optional<User> senderOpt = userRepository.findByEmail(senderEmail);
        Optional<User> receiverOpt = userRepository.findByEmail(receiverEmail);

        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) return false;

        User sender = senderOpt.get();
        User receiver = receiverOpt.get();

        if (sender.equals(receiver)) return false;

        // Verifica se já são amigos
        if (sender.getFriends().contains(receiver)) return false;

        // Verifica se já existe pedido pendente
        Optional<FriendRequest> existingRequest =
                friendRequestRepository.findBySenderAndReceiverAndStatus(sender, receiver, FriendRequest.Status.PENDING);

        if (existingRequest.isPresent()) return false;

        // Cria novo pedido
        FriendRequest request = new FriendRequest();
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(FriendRequest.Status.PENDING);
        friendRequestRepository.save(request);

        // Cria notificação para receptor
        Notification notification = new Notification(
                sender.getFname() + " enviou-te um pedido de amizade",
                NotificationType.FRIEND_REQUEST,
                receiver
        );
        notificationRepository.save(notification);

        return true;
    }

    public List<FriendRequest> getPendingFriendRequests(String receiverEmail) {
        Optional<User> receiverOpt = userRepository.findByEmail(receiverEmail);
        if (receiverOpt.isEmpty()) return List.of();

        return friendRequestRepository.findByReceiverAndStatus(receiverOpt.get(), FriendRequest.Status.PENDING);
    }

    public boolean acceptFriendRequest(Long requestId) {
        Optional<FriendRequest> requestOpt = friendRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) return false;

        FriendRequest request = requestOpt.get();

        if (request.getStatus() != FriendRequest.Status.PENDING) return false;

        User sender = request.getSender();
        User receiver = request.getReceiver();

        // Atualiza status do pedido
        request.setStatus(FriendRequest.Status.ACCEPTED);
        friendRequestRepository.save(request);

        // Adiciona amizade bidirecional
        sender.getFriends().add(receiver);
        receiver.getFriends().add(sender);
        userRepository.save(sender);
        userRepository.save(receiver);

        // Notificação para sender que foi aceite
        Notification notification = new Notification(
                receiver.getFname() + " aceitou o teu pedido de amizade",
                NotificationType.FRIEND_ACCEPTED,
                sender
        );
        notificationRepository.save(notification);

        return true;
    }

    public boolean rejectFriendRequest(Long requestId) {
        Optional<FriendRequest> requestOpt = friendRequestRepository.findById(requestId);

        if (requestOpt.isEmpty()) return false;

        FriendRequest request = requestOpt.get();

        if (request.getStatus() != FriendRequest.Status.PENDING) return false;

        request.setStatus(FriendRequest.Status.REJECTED);
        friendRequestRepository.save(request);

        // Notificação para sender que foi rejeitado (opcional)
        Notification notification = new Notification(
                request.getReceiver().getFname() + " rejeitou o teu pedido de amizade",
                NotificationType.FRIEND_REJECTED,
                request.getSender()
        );
        notificationRepository.save(notification);

        return true;
    }

    public User getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("Utilizador não autenticado.");
        }
        return user;
    }
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email já existe");
        }
        return userRepository.save(user);
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }


    public void changePasswordWithoutOldPassword(String email, String newPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Utilizador não encontrado.");
        }

        if (newPassword.length() < 6) {
            throw new RuntimeException("A nova password deve ter pelo menos 6 caracteres.");
        }

        User user = userOpt.get();
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User updateProfile(User currentUser, User updatedData) {
        currentUser.setFname(updatedData.getFname());
        currentUser.setLname(updatedData.getLname());
        currentUser.setEmail(updatedData.getEmail());
        return userRepository.save(currentUser);
    }

    public boolean addFriend(String userEmail, String friendEmail) {
        Optional<User> userOpt = userRepository.findByEmail(userEmail);
        Optional<User> friendOpt = userRepository.findByEmail(friendEmail);

        if (userOpt.isEmpty() || friendOpt.isEmpty()) return false;

        User user = userOpt.get();
        User friend = friendOpt.get();

        if (user.equals(friend) || user.getFriends().contains(friend)) return false;

        // Relacionamento bidirecional
        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend); // <- Isto é importante para manter consistência!

        // Criação da notificação de amizade
        Notification notification = new Notification(
                user.getFname() + " quer ser teu amigo!",
                NotificationType.FRIEND_REQUEST,
                friend
        );
        notificationRepo.save(notification);

        return true;
    }


}
