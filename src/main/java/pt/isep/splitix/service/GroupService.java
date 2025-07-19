package pt.isep.splitix.service;

import org.springframework.stereotype.Service;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.repository.GroupRepository;
import pt.isep.splitix.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GroupService {
    private final GroupRepository groupRepo;
    private final UserRepository userRepo;

    public GroupService(GroupRepository groupRepo, UserRepository userRepo) {
        this.groupRepo = groupRepo;
        this.userRepo = userRepo;
    }

    public Group createGroup(String name, String creatorEmail, List<String> memberEmails) {
        User creator = userRepo.findByEmail(creatorEmail).orElseThrow();
        Group group = new Group();
        group.setName(name);
        group.setCreator(creator);

        Set<User> members = new HashSet<>();
        for (String email : memberEmails) {
            userRepo.findByEmail(email).ifPresent(members::add);
        }

        members.add(creator);
        group.setMembers(members);

        return groupRepo.save(group);
    }
}

