package com.github.leanfe.controller;

import com.github.leanfe.util.Constants;
import com.github.leanfe.util.Utils;
import javafx.scene.input.MouseEvent;

public class InformationController {

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

    public void openSite(MouseEvent mouseEvent) {
        Utils.openURL(Constants.DEVELOPER_SITE);
    }

    public void openTelegram(MouseEvent mouseEvent) {
        Utils.openURL(Constants.DEVELOPER_TELEGRAM);

    }

    public void openGitHub(MouseEvent mouseEvent) {
        Utils.openURL(Constants.DEVELOPER_GITHUB);
    }

    public void news(MouseEvent mouseEvent) {
        UniversalController.news();
    }
}
