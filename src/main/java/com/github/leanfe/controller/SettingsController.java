package com.github.leanfe.controller;

import com.github.leanfe.settings.SettingsLoader;
import com.github.leanfe.tray.TrayManager;
import com.github.leanfe.util.Constants;
import com.github.leanfe.util.Utils;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable, UniversalController {

    public TextField RamField;
    public TextField JAVA_HOME_Field;
    public CheckBox hideStart;
    public TextField accessToken;
    public TextField UUIDField;
    public ChoiceBox USER_TYPE_BOX;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RamField.setText(SettingsLoader.getProperties().getProperty("RAM"));
        JAVA_HOME_Field.setText(SettingsLoader.getProperties().getProperty("JAVA_HOME"));
        hideStart.setSelected(Boolean.parseBoolean(SettingsLoader.getProperties().getProperty("HIDE_LAUNCHER")));
        accessToken.setText(SettingsLoader.getProperties().getProperty("ACCESS_TOKEN"));

        USER_TYPE_BOX.setItems(FXCollections.observableArrayList("legacy", "mojang"));

        UUIDField.setText(SettingsLoader.getProperties().getProperty("UUID"));
    }

    public void settings(MouseEvent mouseEvent) {
        UniversalController.settings();
    }

    public void exit(MouseEvent mouseEvent) {
        UniversalController.exit();
    }

    public void information(MouseEvent mouseEvent) {
        UniversalController.information();
    }


    public void differentMode(MouseEvent mouseEvent) {
        UniversalController.differentMode();
    }

    public void mainPanel(MouseEvent mouseEvent) {
        UniversalController.mainPanel();
    }

    public void openHelp(MouseEvent mouseEvent) {
        Utils.openURL(Constants.DISCORD_URL);
    }

    public void openFolder(MouseEvent mouseEvent) {
        Utils.openFolder();
    }


    public void saveData(MouseEvent mouseEvent) {
        SettingsLoader.getProperties().replace("RAM", RamField.getText());
        SettingsLoader.getProperties().replace("JAVA_HOME", JAVA_HOME_Field.getText());
        SettingsLoader.getProperties().replace("HIDE_LAUNCHER", hideStart.isSelected());
        SettingsLoader.getProperties().replace("ACCESS_TOKEN", accessToken.getText());
        SettingsLoader.getProperties().replace("UUID", UUIDField.getText());

        TrayManager.displayMessage("Всё готово!", TrayManager.MessageType.INFO);
    }
}
