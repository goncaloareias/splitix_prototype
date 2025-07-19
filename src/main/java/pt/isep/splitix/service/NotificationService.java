package pt.isep.splitix.service;


import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.Notification;
import pt.isep.splitix.domain.Enum.NotificationType;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.NotificationRepository;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(String message, NotificationType type, User recipient) {
        Notification notification = new Notification(message, type, recipient);
        notificationRepository.save(notification);
    }

    public List<Notification> getUnseenNotifications(User user) {
        return notificationRepository.findByRecipientAndSeenFalse(user);
    }

    public void markAsSeen(List<Notification> notifications) {
        for (Notification n : notifications) {
            n.setSeen(true);
        }
        notificationRepository.saveAll(notifications);
    }
}

