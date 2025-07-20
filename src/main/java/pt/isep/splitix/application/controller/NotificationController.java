package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pt.isep.splitix.domain.Notification;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;
import pt.isep.splitix.service.NotificationService;

import java.util.List;

@Controller
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/notifications")
    public String showNotifications(HttpSession session, Model model) {
        User currentUser = userService.getCurrentUser(session);
        List<Notification> notifications = notificationService.getUnseenNotifications(currentUser);
        model.addAttribute("notifications", notifications);
        return "notifications";
    }
}
