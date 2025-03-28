package me.rogerioferreira.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import me.rogerioferreira.sudoku.Point;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.events.MouseMoveEvent;

public class BoardInputProcessor {
  private Board board;
  private EventMediator eventMediator;

  private Point mouseSpacePos = new Point(0, 0);
  private final GameState screenGameState;
  private GameState currentGameState;

  public BoardInputProcessor(Board board, EventMediator eventMediator, GameState screenGameState) {
    this.board = board;
    this.eventMediator = eventMediator;
    this.screenGameState = screenGameState;

    this.eventMediator.addEventListener(event -> {
      if (event instanceof GameTransitionEvent gameTransitionEvent) {
        this.currentGameState = gameTransitionEvent.gameState();
      }
    });

  }

  public void registerAsInputProcessor() {
    var inputProcessor = this;

    this.mouseSpacePos = this.computeMouseSpacePos(
        Gdx.input.getX() - Game.GLOBAL_OFFSET.x(),
        Gdx.input.getY() - Game.GLOBAL_OFFSET.y());

    this.eventMediator.fireEvent(new MouseMoveEvent(this.mouseSpacePos.x(), this.mouseSpacePos.y()));

    Gdx.input.setInputProcessor(new InputAdapter() {
      @Override
      public boolean mouseMoved(int screenX, int screenY) {
        if (inputProcessor.screenGameState != inputProcessor.currentGameState) {
          return super.mouseMoved(screenX, screenY);
        }

        inputProcessor.mouseSpacePos = inputProcessor.computeMouseSpacePos(
            screenX - Game.GLOBAL_OFFSET.x(),
            screenY - Game.GLOBAL_OFFSET.y());

        inputProcessor.eventMediator
            .fireEvent(new MouseMoveEvent(inputProcessor.mouseSpacePos.x(), inputProcessor.mouseSpacePos.y()));

        return super.mouseMoved(screenX, screenY);
      }

      @Override
      public boolean keyDown(int keycode) {
        if (inputProcessor.screenGameState != inputProcessor.currentGameState) {
          return false;
        }

        if (inputProcessor.canChangeSpace(inputProcessor.mouseSpacePos)) {
          if (keycode >= Input.Keys.NUM_1 && keycode <= Input.Keys.NUM_9) {
            var selectedValue = keycode - Input.Keys.NUM_1 + 1;

            board.assignSpace(inputProcessor.getIsInitial(), inputProcessor.mouseSpacePos, selectedValue);
          }

          if (keycode == Input.Keys.NUM_0) {
            board.clearSpace(mouseSpacePos);
          }
        }

        if (keycode == Input.Keys.ENTER) {
          eventMediator.fireEvent(new GameTransitionEvent(GameState.PLAYING));
        }

        return true;
      }
    });

  }

  private int getSpaceSize() {
    return Game.SPACE_SIZE + 1; // Space size + line width
  }

  private Point computeMouseSpacePos(int x, int y) {
    return new Point(
        (int) (x / this.getSpaceSize()),
        (int) (y / this.getSpaceSize()));
  }

  private boolean canChangeSpace(Point position) {
    return this.getIsInitial() || !board.isSpaceFixed(position);
  }

  private boolean getIsInitial() {
    return this.currentGameState == GameState.FIXED_SPACE_ASSIGNEMENT;
  }
}
