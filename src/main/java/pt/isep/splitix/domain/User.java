package pt.isep.splitix.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The first name can not be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]{1,}$", message = "The first name must start with a capital letter")
    @Column(nullable = false)
    private String fname;

    @NotBlank(message = "The last name can not be empty")
    @Pattern(regexp = "^[A-Z][a-zA-Z]{1,}$", message = "The last name must start with a capital letter")
    @Column(nullable = false)
    private String lname;

    @NotBlank(message = "The email is necessary")
    @Email(message = "Invalid email")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A password is necessary")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$",
            message = "A palavra-passe deve ter pelo menos 8 caracteres, 1 maiúscula, 1 minúscula, 1 número e 1 símbolo"
    )
    @Column(nullable = false)
    private String password;

    public User(String fname, String lname, String email, String password) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public User() {}

//getters
/*
    public String getFname() {
        return fname;
    }

    public Email getEmail(){
        return this.email;
    }

 */
}


