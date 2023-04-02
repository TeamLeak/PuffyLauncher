package com.github.leanfe.controller;

import javafx.scene.input.MouseEvent;

public class Mode2Controller {

    public void settings(MouseEvent mouseEvent) {
        UniversalController.settings();
    }

    public void exit(MouseEvent mouseEvent) {
        UniversalController.exit();
    }

    public void information(MouseEvent mouseEvent) {
        UniversalController.information();
    }

    public void mainPanel(MouseEvent mouseEvent) {
        UniversalController.mainPanel();
    }

}
