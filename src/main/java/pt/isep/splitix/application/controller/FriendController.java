package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

@Controller
public class FriendController {

    private final UserService userService;

    public FriendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add-friend")
    public String showAddFriendForm() {
        return "add-friend"; // Thymeleaf template para o form de adicionar amigo
    }

    @PostMapping("/add-friend")
    public String addFriend(@RequestParam("friendEmail") String friendEmail,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        User currentUser = userService.getCurrentUser(session);
        boolean success = userService.sendFriendRequest(currentUser.getEmail(), friendEmail);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Pedido de amizade enviado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro: não foi possível enviar o pedido.");
        }

        return "redirect:/add-friend";
    }
}
