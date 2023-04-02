package com.github.leanfe.game;

import com.github.leanfe.file.FileManager;

import java.util.Objects;

public class GameInformer {

    public static boolean gameInstalled() {
        return FileManager.createGameDir().exists() && !FileManager.createGameDir().isFile() && Objects.requireNonNull(FileManager.createGameDir().listFiles()).length != 0;
    }
}
