package pt.isep.splitix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByCreator(User creator);
}

