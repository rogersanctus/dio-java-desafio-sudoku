package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class StartScreen implements com.badlogic.gdx.Screen {
  private Game game;
  private EventMediator eventMediator;

  private Texture bgTexture;

  public StartScreen(Game game, EventMediator eventMediator) {
    this.game = game;
    this.eventMediator = eventMediator;

    bgTexture = new Texture("background.png");
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
  public void show() {
  }

  @Override
  public void render(float delta) {
    this.checkUserInput();

    this.game.batch.begin();
    this.game.batch.draw(this.bgTexture, 0, 0, Game.GAME_SCREEN_SIZE, Game.GAME_SCREEN_SIZE);
    this.game.font.draw(this.game.batch, "Press to start", Game.GAME_SCREEN_SIZE / 2 - 50,
        Game.GAME_SCREEN_SIZE / 2 - 50);
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
  }

  @Override
  public void dispose() {
  }

}
