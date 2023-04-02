package com.github.leanfe.controller;

import com.github.leanfe.util.Constants;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Set;

public class NewsController implements UniversalController {

    @FXML
    public WebView newsView;
    public Label Mode_Version;
    public Label Mode_Name;

    private boolean isShowed = false;


    public void createWeb() {
        if (isShowed)
            return;

        WebEngine engine = newsView.getEngine();
        engine.load(Constants.NEWS_URL);

        // hide webview scrollbars whenever they appear.
        newsView.getChildrenUnmodifiable().addListener((ListChangeListener<Node>) change -> {
            Set<Node> deadSeaScrolls = newsView.lookupAll(".scroll-bar");
            deadSeaScrolls.forEach(scroll -> scroll.setVisible(false));
        });

        isShowed = true;
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
}
