package com.github.leanfe.controller;

import com.github.leanfe.tray.TrayManager;
import com.github.leanfe.ui.PanelManager;

public interface UniversalController {

    static void exit() {
        TrayManager.displayMessage("Удачи!", TrayManager.MessageType.INFO);
        System.exit(0);
    }

    static void settings() {
        PanelManager.changeContext(PanelManager.Context.SETTINGS);
    }

    static void news() {
        PanelManager.changeContext(PanelManager.Context.NEWS);
    }

    static void information() {
        PanelManager.changeContext(PanelManager.Context.INFORMATION);
    }

    static void differentMode() {
        PanelManager.changeContext(PanelManager.Context.MODE_2);
    }


    static void mainPanel() {
        PanelManager.changeContext(PanelManager.Context.MAIN);
    }
}
