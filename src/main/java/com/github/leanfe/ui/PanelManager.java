package com.github.leanfe.ui;

import com.github.leanfe.util.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PanelManager {

    public static enum Context {
        MAIN,
        SETTINGS,
        MODE_2,
        NEWS,
        INFORMATION
    }

    private static Stage stage;

    public static void init(Stage stage) {
        PanelManager.stage = stage;
    }

    public static void changeContext(Context context) {
        FXMLLoader fxmlLoader = null;

        switch (context) {
            case MAIN -> fxmlLoader = new FXMLLoader(Constants.MAIN_FXML);
            case NEWS -> fxmlLoader = new FXMLLoader(Constants.NEWS_FXML);
            case INFORMATION -> fxmlLoader = new FXMLLoader(Constants.INFORMATION_FXML);
            case MODE_2 -> fxmlLoader = new FXMLLoader(Constants.MODE2_FXML);
            case SETTINGS -> fxmlLoader = new FXMLLoader(Constants.SETTINGS_FXML);
        }

        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            System.err.printf("Error when try load this context: %s !", context);
        }
    }
}
