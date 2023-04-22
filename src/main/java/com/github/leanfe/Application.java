package com.github.leanfe;

import com.github.leanfe.file.FileManager;
import com.github.leanfe.security.SecurityController;
import com.github.leanfe.settings.SettingsLoader;
import com.github.leanfe.util.Constants;
import com.github.leanfe.util.LogManipulation;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {

        LogManipulation.redirectErrorsToFile();

        FileManager.initializeAllDirs();
        SettingsLoader.initializeSettings();

        //Check, if JavaFX installed.
        try {
            Class.forName("javafx.application.Application");

            if (Constants.USE_PROTECTION)
                SecurityController.startSecurity();

            // Load window.
            showWindow();

        } catch (ClassNotFoundException e) {
            System.err.print("Can't find JavaFX!");

            JOptionPane.showMessageDialog(new JFrame(),
                    "Can't find JavaFX! Please, download it manually, \n" +
                    "Or write message to project tech support!");
        }

    }

    private static void showWindow() {
        javafx.application.Application.launch(FXApplication.class);
    }
}