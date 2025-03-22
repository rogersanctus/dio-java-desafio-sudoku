package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.game.Game;
import me.rogerioferreira.sudoku.game.GameState;

public class StartScreen implements com.badlogic.gdx.Screen {
  private Game game;
  private EventMediator eventMediator;

  private Texture bgTexture;
  private Texture sudokuTitleTexture;
  private Texture optionStartTexture;
  private Texture optionExitTexture;
  private Texture optionSelectorTexture;

  private static final float startGameY = Game.GAME_SCREEN_SIZE * 0.5f - 180 + 11;
  private static final float exitGameY = Game.GAME_SCREEN_SIZE * 0.5f - 240 + 1;
  private float selectorY;

  private String optionSelected;

  public StartScreen(Game game, EventMediator eventMediator) {
    this.game = game;
    this.eventMediator = eventMediator;

    bgTexture = new Texture("background.png");
    sudokuTitleTexture = new Texture("sudoku_title.png");
    optionStartTexture = new Texture("start_game_text.png");
    optionExitTexture = new Texture("exit_game_text.png");
    optionSelectorTexture = new Texture("option_selector.png");

    this.optionSelected = "start";
    this.selectorY = startGameY;
  }

  private void checkUserInput() {
    // Navigate between options
    if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
      if (this.optionSelected.equals("start")) {
        this.selectorY = exitGameY;
        this.optionSelected = "exit";
      } else {
        this.selectorY = startGameY;
        this.optionSelected = "start";
      }
    }

    // Start game
    if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      if (this.optionSelected.equals("start")) {
        this.eventMediator.fireEvent(new GameTransitionEvent(GameState.PLAYING));
      } else {
        Gdx.app.exit();
      }
    }
  }

  private void draw() {
    this.game.batch.begin();

    var sudokuTitleTextureX = Game.GAME_SCREEN_SIZE * 0.5f - sudokuTitleTexture.getWidth() * 0.5f;

    this.game.batch.draw(this.bgTexture, 0, 0, Game.GAME_SCREEN_SIZE, Game.GAME_SCREEN_SIZE);

    this.game.batch.draw(this.sudokuTitleTexture, sudokuTitleTextureX,
        Game.GAME_SCREEN_SIZE * 0.5f - sudokuTitleTexture.getHeight());

    this.game.batch.draw(this.optionSelectorTexture, sudokuTitleTextureX, this.selectorY);

    var selectorX = sudokuTitleTextureX + optionSelectorTexture.getWidth();

    this.game.batch.draw(this.optionStartTexture, selectorX + 15, Game.GAME_SCREEN_SIZE * 0.5f - 180);
    this.game.batch.draw(this.optionExitTexture, selectorX + 15, Game.GAME_SCREEN_SIZE * 0.5f - 240);

    this.game.batch.end();
  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
    this.checkUserInput();

    this.draw();
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
    this.bgTexture.dispose();
    this.sudokuTitleTexture.dispose();
    this.optionStartTexture.dispose();
    this.optionExitTexture.dispose();
    this.optionSelectorTexture.dispose();
  }

}
