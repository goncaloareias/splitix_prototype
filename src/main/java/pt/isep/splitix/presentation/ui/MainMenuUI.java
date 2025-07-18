package pt.isep.splitix.presentation.ui;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenuUI {

    private final RegisterUserUI registerUserUI;
    private final Scanner scanner = new Scanner(System.in);
    private final LoginUserUI loginUserUI;

    public MainMenuUI(RegisterUserUI registerUserUI, LoginUserUI loginUserUI) {
        this.registerUserUI = registerUserUI;
        this.loginUserUI = loginUserUI;
    }

    public void show() {
        while (true) {
            System.out.println("\n==== MENU PRINCIPAL ====");
            System.out.println("1. Registar utilizador");
            System.out.println("2. Login utilizador");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> registerUserUI.show();
                case 2 -> loginUserUI.show();
                case 0 -> {
                    System.out.println("Até à próxima!");
                    return;
                }
                default -> System.out.println("Opção inválida");
            }
        }
    }
}

