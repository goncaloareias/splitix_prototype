package pt.isep.splitix.presentation.ui;


import org.springframework.stereotype.Component;
import pt.isep.splitix.application.Console;
import pt.isep.splitix.application.controller.LoginUserController;
import java.util.Scanner;

@Component
public class LoginUserUI {

    private final LoginUserController controller;
    private final Scanner scanner = new Scanner(System.in);

    public LoginUserUI(LoginUserController controller) {
        this.controller = controller;
    }

    public void show() {
        System.out.println("===== LOGIN =====");
        String email = Console.readLine("Email: ");
        String password = Console.readLine("Password: ");

        try {
            var user = controller.login(email, password);
            System.out.println("✅ Login efetuado com sucesso! Bem-vindo, "); //+ user.getFname());
        } catch (Exception e) {
            System.out.println("❌ Erro no login: " + e.getMessage());
        }
    }
}

