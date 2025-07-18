package pt.isep.splitix.presentation.ui;

import org.springframework.stereotype.Component;
import pt.isep.splitix.application.Console;
import pt.isep.splitix.domain.User;
import pt.isep.splitix.service.UserService;

import java.util.Scanner;

@Component
public class RegisterUserUI {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);

    public RegisterUserUI(UserService userService) {
        this.userService = userService;
    }

    public void show() {
        System.out.println("\n--- Registar Utilizador ---");
        String fname = Console.readLine("First Name: ");
        String lname = Console.readLine("Last Name: ");
        String email = Console.readLine("Email: ");
        String password = Console.readLine("Password:");

        User user = new User(fname, lname, email, password);
        try {
            User saved = userService.register(user);
            System.out.println("Utilizador registado: " + saved);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
