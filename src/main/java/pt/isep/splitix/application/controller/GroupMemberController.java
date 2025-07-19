package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.GroupService;
import pt.isep.splitix.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/group/members")
public class GroupMemberController {

    private final GroupService groupService;
    private final UserService userService;

    public GroupMemberController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping
    public String showGroupMembers(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Group> groupOpt = groupService.findByCreator(user);

        if (groupOpt.isEmpty()) {
            model.addAttribute("error", "Não tens grupo criado.");
            return "group-members";  // podes criar esta view para membros
        }

        model.addAttribute("group", groupOpt.get());
        return "group-members"; // view diferente para gerir membros
    }

    @PostMapping("/add-member")
    public String addMember(@RequestParam String memberEmail, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Group> groupOpt = groupService.findByCreator(user);
        if (groupOpt.isEmpty()) {
            model.addAttribute("error", "Não tens grupo para adicionar membros.");
            return "group-members";
        }

        boolean success = groupService.addMember(groupOpt.get().getId(), memberEmail);
        model.addAttribute("group", groupOpt.get());
        if (!success) {
            model.addAttribute("error", "Não foi possível adicionar o membro.");
        } else {
            model.addAttribute("success", "Membro adicionado com sucesso.");
        }
        return "group-members";
    }

    @PostMapping("/remove-member")
    public String removeMember(@RequestParam String memberEmail, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Group> groupOpt = groupService.findByCreator(user);
        if (groupOpt.isEmpty()) {
            model.addAttribute("error", "Não tens grupo para remover membros.");
            return "group-members";
        }

        boolean success = groupService.removeMember(groupOpt.get().getId(), memberEmail);
        model.addAttribute("group", groupOpt.get());
        if (!success) {
            model.addAttribute("error", "Não foi possível remover o membro.");
        } else {
            model.addAttribute("success", "Membro removido com sucesso.");
        }
        return "group-members";
    }
}



