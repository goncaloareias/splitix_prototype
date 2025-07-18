package pt.isep.splitix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.isep.splitix.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
