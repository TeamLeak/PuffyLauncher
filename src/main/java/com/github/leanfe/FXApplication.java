package com.github.leanfe;

import com.github.leanfe.session.SessionManager;
import com.github.leanfe.tray.TrayManager;
import com.github.leanfe.ui.PanelManager;
import com.github.leanfe.util.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FXApplication extends javafx.application.Application {

    private static final SessionManager sessionManager = new SessionManager();

    private static Stage primaryStage;

    public static void hide() {
        primaryStage.hide();
    }

    public static void show() {
        primaryStage.show();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        try {
            stage.setScene(loadScene());
        } catch (IOException e) {
            System.err.printf("Some error occurred! %s", e.getMessage());
            System.exit(1);
        }

        applyStyles(stage);
        setTitle(stage, Constants.APP_NAME);
        setIcon(stage);

        addToTray(stage);
        PanelManager.init(stage);

        show();
    }

    private static Scene loadScene() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Constants.LOGIN_FXML);
        if (sessionManager.load())  fxmlLoader = new FXMLLoader(Constants.MAIN_FXML);

        return new Scene(fxmlLoader.load());
    }

    private static void applyStyles(Stage stage) {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);

        stage.centerOnScreen();
    }

    private static void setTitle(Stage stage, String title) {
        stage.setTitle(title);
    }

    private static void setIcon(Stage stage) {
        try {
            stage.getIcons().add(new Image(new FileInputStream(Constants.ICON)));
            stage.setIconified(true);
        } catch (FileNotFoundException e) {
            System.err.print("Can't set icon!");
            stage.setIconified(false);
        }
    }

    private static void addToTray(Stage stage) {
        TrayManager.setTrayIcon(stage);
    }
}
