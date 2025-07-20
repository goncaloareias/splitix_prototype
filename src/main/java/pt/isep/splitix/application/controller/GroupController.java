package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.GroupRepository;
import pt.isep.splitix.repository.UserRepository;
import pt.isep.splitix.service.GroupService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class GroupController {

    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public GroupController(GroupService groupService, GroupRepository groupRepository, UserRepository userRepository) {
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/group-create")
    public String showCreateForm() {
        return "group-create";
    }

    @PostMapping("/group-create")
    public String createGroup(
            @RequestParam("name") String groupName,
            @RequestParam(value = "memberEmails", required = false) String memberEmailsRaw,
            HttpSession session,
            Model model
    ) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<String> memberEmails;
        if (memberEmailsRaw == null || memberEmailsRaw.trim().isEmpty()) {
            memberEmails = new ArrayList<>();
        } else {
            memberEmails = Arrays.stream(memberEmailsRaw.split(","))
                    .map(String::trim)
                    .filter(email -> !email.isEmpty())
                    .collect(Collectors.toList());
        }

        try {
            groupService.createGroup(groupName, user.getEmail(), memberEmails);
            model.addAttribute("success", "Grupo criado com sucesso.");
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao criar grupo: " + e.getMessage());
        }

        return "group-create";
    }



    @GetMapping("/group")
    public String showGroup(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<Group> groups = groupService.findByCreator(user);
        model.addAttribute("groups", groups);

        return "group";
    }

    @GetMapping("/group/{groupId}")
    public String showGroupDetails(@PathVariable Long groupId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Group group = groupService.findById(groupId);
        if (group == null || !group.getCreator().getId().equals(user.getId())) {
            model.addAttribute("error", "Grupo não encontrado ou acesso negado.");
            return "group";
        }

        model.addAttribute("group", group);
        return "group-members";
    }
}
