package pt.isep.splitix.service;

import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.Notification;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getUnseenNotifications(User user) {
        return notificationRepository.findByRecipientAndSeenFalse(user);
    }
}
