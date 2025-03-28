package me.rogerioferreira.sudoku;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import me.rogerioferreira.sudoku.game.Game;

public class Aplication {
  public static void main(String[] args) {
    Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
    config.setTitle("Sudoku");
    config.setWindowedMode(Game.GAME_SCREEN_WIDTH, Game.GAME_SCREEN_HEIGHT); // Window size
    config.useVsync(true); // Enable VSync
    config.setForegroundFPS(60); // Cap FPS
    new Lwjgl3Application(new Game(), config);
  }
}
