package pt.isep.splitix.domain;

import jakarta.persistence.*;
import lombok.*;
import pt.isep.splitix.domain.Enum.NotificationType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean seen;

    @ManyToOne
    private User recipient;

    public Notification() {}

    public Notification(String message, NotificationType type, User recipient) {
        this.message = message;
        this.type = type;
        this.recipient = recipient;
        this.seen = false;
    }

    // Getters e setters
}

