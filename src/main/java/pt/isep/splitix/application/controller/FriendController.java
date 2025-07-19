package pt.isep.splitix.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pt.isep.splitix.service.UserService;

@Controller
public class FriendController {

    private final UserService userService;

    public FriendController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add-friend")
    public String showAddFriendForm() {
        return "add-friend";  // isto corresponde ao ficheiro templates/add.friends.html
    }

    @PostMapping("/add-friend")
    public String addFriend(@RequestParam("friendEmail") String friendEmail,
                            RedirectAttributes redirectAttributes) {
        // Supondo que tens uma lógica para obter o utilizador atual (exemplo simplificado)
        String currentUserEmail = "userActual"; // substitui isto pela lógica real

        boolean success = userService.addFriend(currentUserEmail, friendEmail);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Amigo adicionado com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro: não foi possível adicionar o amigo.");
        }

        return "redirect:/add-friend";
    }
}


