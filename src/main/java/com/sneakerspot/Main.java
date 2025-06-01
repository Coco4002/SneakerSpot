package com.sneakerspot;

import com.sneakerspot.ui.auth.LoginScreen;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Look & feel opțional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}