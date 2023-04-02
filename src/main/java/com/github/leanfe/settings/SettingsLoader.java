package com.github.leanfe.settings;

import com.github.leanfe.file.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SettingsLoader {

    // TODO: Create settings manager.

    private static final File dir = FileManager.createSettingsDir();
    private static final File propertiesFile = new File(dir, "settings.properties");

    private static final Properties p = new Properties();

    public static void initializeSettings() {
        if (!dir.exists() || dir.isFile()) {
            dir.mkdir();
        }
        if (!propertiesFile.exists() || propertiesFile.isDirectory()) {
            propertiesFile.delete();
            try {
                propertiesFile.createNewFile();
                defaultValues();
            } catch (IOException e) {
                System.err.printf("Can't create settings! LOG: %s", e.getMessage());
            }
        }

        initializeMap();
    }

    private static void initializeMap() {
        try {
            FileReader reader = new FileReader(propertiesFile);

            p.load(reader);
        } catch (FileNotFoundException e) {
            initializeSettings();
        } catch (IOException e) {
            System.err.printf("Can't open settings! LOG: %s", e.getMessage());
            propertiesFile.delete();
            initializeSettings();
        }

    }

    private static void defaultValues() {
        p.put("RAM", "4096");
        p.put("JAVA_HOME", FileManager.getJavaFile());
        p.put("HIDE_LAUNCHER", false);
        p.put("ACCESS_TOKEN", "PUFFYMC");
        p.put("UUID", "00000000-0000-0000-0000-000000000000");
        p.put("USER_TYPE", "legacy");
    }

    public static Properties getProperties() {
        return p;
    }
}
