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

import java.util.List;
import java.util.Optional;

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
    public String createGroup(@RequestParam("name") String groupName, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        if (user.getId() == null) {
            throw new IllegalStateException("User não está persistido na base de dados.");
        }

        Group group = new Group();
        group.setName(groupName);
        group.setCreator(user); // define o criador
        // NÃO adiciona membros

        groupRepository.save(group);

        return "redirect:/group";
    }



    @GetMapping("/group")
    public String showGroup(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        List<Group> groups = groupService.findByCreator(user);

        if (groups.isEmpty()) {
            model.addAttribute("error", "Não tens grupo criado.");
            return "group";
        }

        model.addAttribute("groups", groups);
        return "group";
    }

}
