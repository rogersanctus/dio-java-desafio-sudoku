package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Screen;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.game.Board;
import me.rogerioferreira.sudoku.game.BoardDrawer;
import me.rogerioferreira.sudoku.game.BoardInputProcessor;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class FixedSpaceAssignmentScreen implements Screen {
  private BoardInputProcessor boardInputProcessor;
  private BoardDrawer boardDrawer;

  public FixedSpaceAssignmentScreen(Game game, Board board, EventMediator eventMediator) {
    System.out.println("FixedSpaceAssignmentScreen created!");

    this.boardInputProcessor = new BoardInputProcessor(board, eventMediator, GameState.FIXED_SPACE_ASSIGNEMENT);
    this.boardDrawer = new BoardDrawer(game, board, eventMediator);
  }

  @Override
  public void show() {
    System.out.println("FixedSpaceAssignmentScreen opened!");
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
    System.out.println("FixedSpaceAssignmentScreen closed!");
  }

  @Override
  public void dispose() {
    this.boardDrawer.dispose();
  }

}
