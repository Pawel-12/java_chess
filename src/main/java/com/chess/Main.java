package com.chess;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.chess.model.ChessGame;


public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("CHESS");
        config.setWindowedMode(480, 480);
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setResizable(false);

        new Lwjgl3Application(new ChessGame(480, 480), config);
    }
}
