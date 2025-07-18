/*package pt.isep.splitix.presentation.ui;


import org.springframework.stereotype.Component;
import pt.isep.splitix.application.Console;
import pt.isep.splitix.application.controller.LoginUserController;
import pt.isep.splitix.domain.User;

import java.util.Optional;
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

        Optional<User> userOpt = controller.login(email, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            System.out.println("Login efetuado com sucesso! Bem vindo, " + user.getFname());
        } else {
            System.out.println("Credenciais inválidas.");
            System.out.println("Esqueceu a password? (s/n)");
            String option = scanner.nextLine().trim().toLowerCase();

            if (option.equals("s")) {
                resetPasswordFlow(email);
            }
        }
    }

    private void resetPasswordFlow(String email) {
        System.out.println("===== RESET PASSWORD =====");

        if (controller.findByEmail(email).isEmpty()) {
            System.out.println("Email não registado.");
            return;
        }

        String newPassword, confirmPassword;
        do {
            System.out.print("Introduza nova password: ");
            newPassword = scanner.nextLine();

            System.out.print("Confirme a nova password: ");
            confirmPassword = scanner.nextLine();

            if (!newPassword.equals(confirmPassword)) {
                System.out.println("Passwords não coincidem. Tente novamente.");
            }
        } while (!newPassword.equals(confirmPassword));

        try {
            controller.changePasswordWithoutOldPassword(email, newPassword);
            System.out.println("Password alterada com sucesso! Faça login com a nova password.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao alterar password: " + e.getMessage());
        }
    }
}
*/
