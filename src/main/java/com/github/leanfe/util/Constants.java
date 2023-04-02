package com.github.leanfe.util;

import com.github.leanfe.Application;

import java.io.File;
import java.net.URL;

public class Constants {
    public static final String APP_NAME = "PuffyMC";
    public static final File ICON = new File(Application.class.getResource("icon/icon.png").getFile());

    public static final URL MAIN_FXML = Application.class.getResource("main.fxml");
    public static final URL SETTINGS_FXML = Application.class.getResource("settings.fxml");
    public static final URL NEWS_FXML = Application.class.getResource("news.fxml");
    public static final URL INFORMATION_FXML = Application.class.getResource("information.fxml");
    public static final URL MODE2_FXML = Application.class.getResource("mode2.fxml");
    public static final URL LOGIN_FXML = Application.class.getResource("login.fxml");

    public static final boolean useSessions = false;

    public static final boolean USE_DISCORD = true;
    public static final boolean USE_VK = true;
    public static final boolean USE_YOUTUBE = true;

    public static final String DISCORD_URL = "https://discord.gg/F5JZuXjKG2";
    public static final String VK_URL = "https://vk.com/asykyy";
    public static final String YOUTUBE_URL = "https://www.youtube.com/channel/UCRl9x2DdcVkTlt1lutTfJnA";

    public static final String RESET_URL = "https://t.me/norfitspire";

    public static final String UPDATES_URL = "https://localhost:3000/updates";
    public static final String INFORMATION_URL = "http://localhost:3000/information";

    public static final String NEWS_URL = "http://localhost:3000/launcherUI";

    public static final String DEVELOPER_SITE = "https://leanfe.github.io";
    public static final String DEVELOPER_TELEGRAM = "https://t.me/norfitspire";
    public static final String DEVELOPER_GITHUB = "https://github.com/Leanfe";

    public static final boolean IS_FORGED = false;
}
