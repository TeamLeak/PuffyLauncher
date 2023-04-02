package com.github.leanfe.file;

import com.github.leanfe.util.Constants;

import java.io.File;

public class FileManager {

    private static final String fileSeparator = File.separator;

    public static File initializeApplicationFolder() {
        final String userHome = System.getProperty("user.home");

        File folder;

        switch (OperatingSystem.getCurrentlyPlatform()) {
            case WINDOWS ->
                    folder = new File(userHome + fileSeparator + "AppData" + fileSeparator + "Roaming" + fileSeparator + Constants.APP_NAME);
            case MACOS ->
                    folder = new File(userHome + fileSeparator + "Library" + fileSeparator + "Application Support" + fileSeparator + Constants.APP_NAME);
            default -> folder = new File(userHome + fileSeparator + Constants.APP_NAME);
        }

        if(!folder.exists())
            folder.mkdir();

        return folder;
    }

    public static File createGameDir() {
        var gameFolder = new File(initializeApplicationFolder(), "game");

        if (!gameFolder.exists() || gameFolder.isFile())
            gameFolder.mkdir();

        return gameFolder;
    }

    public static File createSessionDir() {
        var sessionFolder = new File(initializeApplicationFolder(), "session");

        if (!sessionFolder.exists() || sessionFolder.isFile())
            sessionFolder.mkdir();

        return sessionFolder;
    }

    public static File createSettingsDir() {
        var settingsDir = new File(initializeApplicationFolder(), "settings");

        if (!settingsDir.exists() || settingsDir.isFile())
            settingsDir.mkdir();

        return settingsDir;
    }

    public static void initializeAllDirs() {
        initializeApplicationFolder();
        createSettingsDir();
        createSessionDir();
        createGameDir();
    }

    public static File createJavaDir() {
        return new File(createGameDir(), "native");
    }

    public static File getJavaFile() {
        var temp = new File(FileManager.createJavaDir(), "bin");

        if (OperatingSystem.getCurrentlyPlatform().equals(OperatingSystem.WINDOWS))
            return new File(temp, "java.exe");
        else
            return new File(temp, "java");
    }
}
