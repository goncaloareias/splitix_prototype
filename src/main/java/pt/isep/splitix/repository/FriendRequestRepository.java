package pt.isep.splitix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.isep.splitix.domain.FriendRequest;
import pt.isep.splitix.domain.User;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverAndStatus(User receiver, FriendRequest.Status status);
    Optional<FriendRequest> findBySenderAndReceiverAndStatus(User sender, User receiver, FriendRequest.Status status);
}
