package com.github.leanfe.tray;

import com.github.leanfe.util.Constants;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;

public class TrayManager {

    private static int stateWindow = 1;
    private static TrayIcon trayIcon;

    public static void setTrayIcon(Stage stage) {

        if (!SystemTray.isSupported()) {
            return;
        }

        final PopupMenu popup = new PopupMenu();
        final MenuItem exit = new MenuItem("Выход!");
        final SystemTray tray = SystemTray.getSystemTray();

        ActionListener menuActionListener = e -> System.exit(0);

        URL imageURL = null;
        try {
            imageURL = Constants.ICON.toURL();
        } catch (MalformedURLException e) {
            System.err.print("Error, when parsing tray-icon!");
        }

        Image icon = Toolkit.getDefaultToolkit().getImage(imageURL);

        trayIcon = new TrayIcon(icon, Constants.APP_NAME, popup);
        trayIcon.setImageAutoSize(true);

        exit.addActionListener(menuActionListener);
        popup.add(exit);

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) Platform.runLater(() -> {
                    if (stateWindow == 1) {
                        stage.hide();
                        stateWindow = 0;
                    } else if (stateWindow == 0) {
                        stage.show();
                        stateWindow = 1;
                    }
                });
            }
        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }

        Platform.setImplicitExit(false);

        trayIcon.displayMessage(Constants.APP_NAME, "Готов к работе", TrayIcon.MessageType.INFO);
    }

    public static void displayMessage(String text, MessageType TYPE) {
        switch (TYPE) {
            case INFO -> trayIcon.displayMessage(Constants.APP_NAME, text, TrayIcon.MessageType.INFO);
            case WARN -> trayIcon.displayMessage(Constants.APP_NAME, text, TrayIcon.MessageType.WARNING);
            case ERROR -> trayIcon.displayMessage(Constants.APP_NAME, text, TrayIcon.MessageType.ERROR);
        }
    }

    public static enum MessageType {
        INFO,
        ERROR,
        WARN
    }
}