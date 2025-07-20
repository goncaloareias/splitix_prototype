package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.FriendRequest;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendRequestController {

    private final UserService userService;

    public FriendRequestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/requests")
    public String showFriendRequests(HttpSession session, Model model) {
        User currentUser = userService.getCurrentUser(session);
        List<FriendRequest> pendingRequests = userService.getPendingFriendRequests(currentUser.getEmail());
        model.addAttribute("requests", pendingRequests);
        return "friend-requests"; // Template para listar pedidos pendentes
    }

    @PostMapping("/requests/{id}/accept")
    public String acceptRequest(@PathVariable Long id) {
        userService.acceptFriendRequest(id);
        return "redirect:/friends/requests";
    }

    @PostMapping("/requests/{id}/reject")
    public String rejectRequest(@PathVariable Long id) {
        userService.rejectFriendRequest(id);
        return "redirect:/friends/requests";
    }
}
