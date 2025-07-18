package pt.isep.splitix.presentation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pt.isep.splitix.presentation.ui.MainMenuUI;

@Component
public class MainRunner implements CommandLineRunner {

    private final MainMenuUI mainMenu;

    public MainRunner(MainMenuUI mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void run(String... args) {
        mainMenu.show();
    }
}
