package pt.isep.splitix.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pt.isep.splitix.service.GroupService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group-create")
    public String showCreateForm() {
        return "group-create";  // só devolve o template, não precisa de model
    }

    @PostMapping("/create")
    public String createGroup(
            @RequestParam String name,
            @RequestParam String memberEmails,  // string com emails separados por vírgula
            Principal principal
    ) {
        List<String> emails = Arrays.stream(memberEmails.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        groupService.createGroup(name, principal.getName(), emails);

        return "redirect:/dashboard";
    }
}


