package pt.isep.splitix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.isep.splitix.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
