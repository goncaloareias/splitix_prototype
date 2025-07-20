package pt.isep.splitix.service;

import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.GroupRepository;
import pt.isep.splitix.repository.UserRepository;

import java.util.*;

@Service
public class GroupService {
    private final GroupRepository groupRepo;
    private final UserRepository userRepo;

    public GroupService(GroupRepository groupRepo, UserRepository userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
    }

    public Group createGroup(String name, String creatorEmail, List<String> memberEmails) {
        User creator = userRepo.findByEmail(creatorEmail).orElseThrow(() -> new IllegalArgumentException("Creator not found"));
        Group group = new Group();
        group.setName(name);
        group.setCreator(creator);

        Set<User> members = new HashSet<>();

        if (memberEmails != null) {
            for (String email : memberEmails) {
                userRepo.findByEmail(email).ifPresent(members::add);
            }
        }

        // Aqui tens que decidir: queres sempre adicionar o criador como membro?
        // Se sim, adiciona:
        members.add(creator);

        group.setMembers(members);

        return groupRepo.save(group);
    }




    public List<Group> findByCreator(User creator) {
        return groupRepo.findByCreator(creator);
    }

    public boolean addMember(Long groupId, String memberEmail) {
        Optional<Group> groupOpt = groupRepo.findById(groupId);
        Optional<User> userOpt = userRepo.findByEmail(memberEmail);

        if (groupOpt.isEmpty() || userOpt.isEmpty()) {
            return false;
        }

        Group group = groupOpt.get();
        User user = userOpt.get();

        boolean added = group.getMembers().add(user);
        if (added) {
            groupRepo.save(group);
        }

        return added;
    }

    public boolean removeMember(Long groupId, String memberEmail) {
        Optional<Group> groupOpt = groupRepo.findById(groupId);
        Optional<User> userOpt = userRepo.findByEmail(memberEmail);

        if (groupOpt.isEmpty() || userOpt.isEmpty()) {
            return false;
        }

        Group group = groupOpt.get();
        User user = userOpt.get();

        if (user.equals(group.getCreator())) {
            // Não podes remover o criador
            return false;
        }

        boolean removed = group.getMembers().remove(user);
        if (removed) {
            groupRepo.save(group);
        }

        return removed;
    }

    public Group findById(Long groupId) {
        return groupRepo.findById(groupId).orElse(null);
    }

}

