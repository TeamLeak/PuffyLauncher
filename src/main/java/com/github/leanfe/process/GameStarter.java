package com.github.leanfe.process;

import com.github.leanfe.FXApplication;
import com.github.leanfe.file.FileManager;
import com.github.leanfe.settings.SettingsLoader;
import com.github.leanfe.util.Constants;

import java.io.File;
import java.io.IOException;

public class GameStarter {

    private final static String UUID = (String) SettingsLoader.getProperties().getOrDefault("UUID", "00000000-0000-0000-0000-000000000000");
    private final static String CLIENT = new File(FileManager.createGameDir(), "client.jar").getAbsolutePath();
    private final static int RAM = Integer.parseInt(SettingsLoader.getProperties().getProperty("RAM"));
    private final static String ACCESS_TOKEN = SettingsLoader.getProperties().getProperty("ACCESS_TOKEN");

    private final static String USER_PROPERTIES = "[]";
    private final static String USER_TYPE = (String) SettingsLoader.getProperties().getOrDefault("USER_TYPE", "legacy");

    private final static String VERSION_TYPE = "Forge";
    private final static String TWEAK_CLASS = "net.minecraftforge.fml.common.launcher.FMLTweaker";


    private static String getArguments(String version) {

        return Constants.IS_FORGED ?
                "-Xms%dM -jar %s --version %s --accessToken %s --uuid %s --userProperties %s --userType %s --tweakClass %s --versionType %s --gamedir ."
                .formatted(RAM,
                        CLIENT,
                        version, ACCESS_TOKEN,
                        UUID, USER_PROPERTIES, USER_TYPE,
                        TWEAK_CLASS, VERSION_TYPE) :
                "-Xms%dM -jar %s --version %s --accessToken %s --uuid %s --userProperties %s --userType %s --gamedir ."
                .formatted(RAM,
                        CLIENT,
                        version, ACCESS_TOKEN,
                        UUID, USER_PROPERTIES, USER_TYPE);

    }

    public static Process startGame(String version) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(SettingsLoader.getProperties().getProperty("JAVA_HOME"), getArguments(version));
        pb.directory(FileManager.createGameDir());

        if (Boolean.parseBoolean(SettingsLoader.getProperties().getProperty("HIDE_LAUNCHER")))
            FXApplication.hide();

        return pb.start();
    }
}
