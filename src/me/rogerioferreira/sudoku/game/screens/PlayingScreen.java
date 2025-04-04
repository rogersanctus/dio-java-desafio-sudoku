package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.game.Board;
import me.rogerioferreira.sudoku.game.BoardDrawer;
import me.rogerioferreira.sudoku.game.BoardInputProcessor;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class PlayingScreen implements Screen {
  private Game game;
  private Board board;
  private EventMediator eventMediator;

  private BoardInputProcessor boardInputProcessor;
  private BoardDrawer boardDrawer;

  private boolean hasCompletedGame = false;

  public PlayingScreen(Game game, Board board, EventMediator eventMediator) {
    this.game = game;
    this.board = board;
    this.eventMediator = eventMediator;

    System.out.println("PlayingScreen created!");

    this.boardInputProcessor = new BoardInputProcessor(board, eventMediator, GameState.PLAYING);
    this.boardDrawer = new BoardDrawer(game, board, eventMediator);
    this.boardDrawer.setMouseRectColor(new Color(0, 0, 1, 0.1f));
  }

  public void reset() {
    this.hasCompletedGame = false;
  }

  private void processLogic() {
    if (!this.hasCompletedGame && this.board.isBoardComplete()) {
      this.eventMediator.fireEvent(new GameTransitionEvent(GameState.COMPLETED));
      this.hasCompletedGame = true;
    }
  }

  @Override
  public void show() {
    System.out.println("PlayingScreen opened!");
    this.boardInputProcessor.registerAsInputProcessor();
  }

  @Override
  public void render(float delta) {
    this.processLogic();
    this.boardDrawer.draw();

    this.game.batch.begin();

    if (this.game.getGameState() == GameState.COMPLETED) {
      this.game.font.setColor(0, 0.8f, 0.2f, 1);

      this.game.font.draw(this.game.batch, "Jogo Finalizado.", 10,
          Game.GAME_SCREEN_SIZE + Game.GLOBAL_OFFSET.y() - 10);

      this.game.font.draw(this.game.batch, "<<ESC>> para voltar ao Início.",
          Game.GAME_SCREEN_WIDTH - 220,
          Game.GAME_SCREEN_SIZE + Game.GLOBAL_OFFSET.y() - 10);
    } else if (this.board.hasInvalidAssignments()) {
      this.game.font.setColor(1, 0, 0, 1);

      this.game.font.draw(this.game.batch, "Existe um ou mais valores inválidos.", 10,
          Game.GAME_SCREEN_SIZE + Game.GLOBAL_OFFSET.y() - 10);

    } else {
      this.game.font.setColor(0, 0.8f, 0.2f, 1);

      this.game.font.draw(this.game.batch, "Preencha todos os espaços para VENCER!", 10,
          Game.GAME_SCREEN_SIZE + Game.GLOBAL_OFFSET.y() - 10);
    }

    this.game.batch.end();
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
