package com.sneakerspot;
import com.sneakerspot.dao.DatabaseInitializer;
import com.sneakerspot.model.User;
import com.sneakerspot.ui.auth.RegistrationScreen;
import com.sneakerspot.ui.auth.StartScreen;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            StartScreen start = new StartScreen();
            start.setVisible(true);
        });
    }
}