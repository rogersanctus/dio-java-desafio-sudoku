package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Gdx;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class StartScreen implements Screen {
  private EventMediator eventMediator;

  public StartScreen(EventMediator eventMediator) {
    this.eventMediator = eventMediator;
  }

  private void checkUserInput() {
    var mouseX = Gdx.input.getX();
    var mouseY = Gdx.input.getY();

    if (Gdx.input.justTouched() && mouseX > Game.GAME_SCREEN_SIZE / 2 - 50 && mouseX < Game.GAME_SCREEN_SIZE / 2 + 50
        && mouseY > Game.GAME_SCREEN_SIZE / 2 - 50 && mouseY < Game.GAME_SCREEN_SIZE / 2 + 50) {
      this.eventMediator.fireEvent(new GameTransitionEvent(GameState.FIXED_SPACE_ASSIGNEMENT));
    }

  }

  @Override
  public void create() {
  }

  @Override
  public void render() {
    this.checkUserInput();
  }

  @Override
  public void dispose() {
  }

}
