package com.github.leanfe.controller;

import com.github.leanfe.tray.TrayManager;
import com.github.leanfe.ui.PanelManager;
import com.github.leanfe.util.AuthManager;
import com.github.leanfe.util.Constants;
import com.github.leanfe.util.Utils;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField login;
    public PasswordField password;
    public ImageView youtube;
    public ImageView vk;
    public ImageView discord;
    public AnchorPane LoginPanel;
    public Label TITLE;
    public Label ERROR_MESSAGE;

    public void processLogin() {
        // TODO:
        boolean result = AuthManager.processLogin(login.getText(), password.getText());

        if (result) {
            PanelManager.changeContext(PanelManager.Context.MAIN);
        } else {
            TrayManager.displayMessage("Не могу войти! Проверьте LOGIN/PASSOWRD!", TrayManager.MessageType.ERROR);

            TITLE.setStyle("-fx-text-fill: #FF0000;");
            TITLE.setText("ОШИБКА ВХОДА");
            ERROR_MESSAGE.setVisible(true);
        }
    }

    public void resetPassword() {
        Utils.openURL(Constants.RESET_URL);
    }

    public void close() {
        TrayManager.displayMessage("Удачи!", TrayManager.MessageType.INFO);
        System.exit(0);
    }

    public void openDiscord() {
        Utils.openURL(Constants.DISCORD_URL);
    }

    public void openVK() {
        Utils.openURL(Constants.VK_URL);
    }

    public void openYoutube() {
        Utils.openURL(Constants.YOUTUBE_URL);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!Constants.USE_YOUTUBE) youtube.setVisible(false);
        if (!Constants.USE_VK) vk.setVisible(false);
        if (!Constants.USE_DISCORD) discord.setVisible(false);

    }

}
