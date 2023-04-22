package com.github.leanfe.controller;

import com.github.leanfe.FXApplication;
import com.github.leanfe.file.FileManager;
import com.github.leanfe.information.InformationLoader;
import com.github.leanfe.process.GameStarter;
import com.github.leanfe.tray.TrayManager;
import com.github.leanfe.updates.DownloadJob;
import com.github.leanfe.updates.DownloadListener;
import com.github.leanfe.updates.Updater;
import com.github.leanfe.util.Constants;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.github.leanfe.game.GameInformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.github.leanfe.util.Utils.readJsonFromUrl;

public class BaseController implements Initializable, DownloadListener, UniversalController {

    @FXML
    public JFXTextArea Information_mode;
    public ProgressBar progressBar;
    public Label timer_download;
    public JFXButton processButton;

    private final boolean isGameInstalled = GameInformer.gameInstalled();
    @FXML
    public Label Mode_Name;
    @FXML
    public Label Mode_Version;

    private final ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);

    boolean isLoaded = false;

    public void process() {
        if (!isGameInstalled) {
            DownloadJob downloadJob = new DownloadJob("Download Game", this);
            Updater javaUpdater = new Updater(Constants.UPDATES_URL, FileManager.createGameDir(), downloadJob);
            javaUpdater.start();

            progressBar = new ProgressBar(downloadJob.getAllFiles().size());
            downloadJob.startDownloading(executorService);
        }

        runDaemon();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (isGameInstalled) processButton.setText("ЗАПУСТИТЬ");
    }

    @Override
    public void onDownloadJobFinished(DownloadJob job) {
        progressBar.setProgress(0);
        progressBar.setVisible(false);
        timer_download.setText("Готово!");
        timer_download.setVisible(false);

        processButton.setText("ЗАПУСТИТЬ");

        TrayManager.displayMessage("Все файлы загружены!", TrayManager.MessageType.INFO);
    }

    @Override
    public void onDownloadJobProgressChanged(DownloadJob job) {
        progressBar.setProgress(job.getAllFiles().size() - job.getRemainingFiles().size());
        timer_download.setText("Загружено: " + (job.getRemainingFiles().size() / job.getAllFiles().size()) * 100 + "%");
    }

    @Override
    public void onDownloadJobStarted(DownloadJob job) {
        progressBar.setVisible(true);
        progressBar.setProgress(0);
        timer_download.setVisible(true);
    }

    public void showUpdates() {
        if (isLoaded)
            return;

        try {
            InformationLoader.Information information = InformationLoader.Converter.fromJsonString(readJsonFromUrl(Constants.INFORMATION_URL));
            Mode_Version.setText("TAGS: " + information.jsonObject.serverTags + "\nVERSION: " + information.jsonObject.version);
            Mode_Name.setText(information.jsonObject.servername);
            Information_mode.setText(information.jsonObject.description);
        } catch (IOException e) {
            TrayManager.displayMessage("Ошибка загрузки информации!", TrayManager.MessageType.WARN);
            System.err.printf("Can't load information! LOG: %s", e.getMessage());
        }

        isLoaded = true;
    }

    public void settings(MouseEvent mouseEvent) {
        UniversalController.settings();
    }

    public void exit(MouseEvent mouseEvent) {
        UniversalController.exit();
    }

    public void news(MouseEvent mouseEvent) {
        UniversalController.news();
    }


    public void information(MouseEvent mouseEvent) {
        UniversalController.information();
    }


    public void differentMode(MouseEvent mouseEvent) {
        UniversalController.differentMode();
    }

    private static void runDaemon() {
        Runnable runnable = () -> {
            try {
                Process game = GameStarter.startGame(Constants.VERSION_ID);

                game.wait();

            } catch (IOException e) {
                TrayManager.displayMessage("Ошибка запуска игры!", TrayManager.MessageType.ERROR);
                System.err.printf("Start com.github.leanfe.game error. LOG: %s", e.getMessage());
            } catch (InterruptedException e) {
                System.err.printf("Start com.github.leanfe.game wait error. LOG: %s", e.getMessage());
            }

            FXApplication.show();
        };

        runnable.run();
    }
}
