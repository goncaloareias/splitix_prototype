package pt.isep.splitix.application.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.GroupService;

@Controller
@RequestMapping("/group")
public class GroupMemberController {

    private final GroupService groupService;

    public GroupMemberController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/{groupId}/add-member")
    public String addMember(@PathVariable Long groupId,
                            @RequestParam String memberEmail,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Group group = groupService.findById(groupId);
        if (group == null || !group.getCreator().getId().equals(user.getId())) {
            model.addAttribute("error", "Grupo inválido ou sem permissão.");
            return "group";
        }

        boolean success = groupService.addMember(groupId, memberEmail);
        model.addAttribute("group", groupService.findById(groupId));
        model.addAttribute(success ? "success" : "error", success ? "Membro adicionado com sucesso." : "Não foi possível adicionar o membro.");
        return "group-members";
    }

    @PostMapping("/{groupId}/remove-member")
    public String removeMember(@PathVariable Long groupId,
                               @RequestParam String memberEmail,
                               Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        Group group = groupService.findById(groupId);
        if (group == null || !group.getCreator().getId().equals(user.getId())) {
            model.addAttribute("error", "Grupo inválido ou sem permissão.");
            return "group";
        }

        boolean success = groupService.removeMember(groupId, memberEmail);
        model.addAttribute("group", groupService.findById(groupId));
        model.addAttribute(success ? "success" : "error", success ? "Membro removido com sucesso." : "Não foi possível remover o membro.");
        return "group-members";
    }
}
