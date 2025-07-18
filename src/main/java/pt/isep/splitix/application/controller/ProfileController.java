package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.UserRepository;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@SessionAttributes("user")
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";  // <-- corrigido aqui
        }
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping
    public String editProfile(@ModelAttribute("user") User updatedUser,
                              HttpSession session,
                              Model model) {

        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return "redirect:/login";
        }

        sessionUser.setFname(updatedUser.getFname());
        sessionUser.setLname(updatedUser.getLname());
        sessionUser.setEmail(updatedUser.getEmail());

        userRepository.save(sessionUser);
        session.setAttribute("user", sessionUser);

        model.addAttribute("user", sessionUser);
        model.addAttribute("success", "Perfil atualizado com sucesso.");
        return "profile";
    }
}
