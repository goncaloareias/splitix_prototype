package pt.isep.splitix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.isep.splitix.domain.Group;
import pt.isep.splitix.domain.User;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    // Muda para devolver lista, porque um user pode ter vários grupos
    List<Group> findByCreator(User creator);

}
