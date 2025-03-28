package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.game.Board;
import me.rogerioferreira.sudoku.game.BoardDrawer;
import me.rogerioferreira.sudoku.game.BoardInputProcessor;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class PlayingScreen implements Screen {
  private BoardInputProcessor boardInputProcessor;
  private BoardDrawer boardDrawer;

  public PlayingScreen(Game game, Board board, EventMediator eventMediator) {
    System.out.println("PlayingScreen created!");

    this.boardInputProcessor = new BoardInputProcessor(board, eventMediator, GameState.PLAYING);
    this.boardDrawer = new BoardDrawer(game, board, eventMediator);
    this.boardDrawer.setMouseRectColor(new Color(0, 0, 1, 0.1f));
  }

  @Override
  public void show() {
    System.out.println("PlayingScreen opened!");
    this.boardInputProcessor.registerAsInputProcessor();
  }

  @Override
  public void render(float delta) {
    this.boardDrawer.draw();
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public void hide() {
    System.out.println("PlayingScreen closed!");
  }

  @Override
  public void dispose() {
    this.boardDrawer.dispose();
  }

}
