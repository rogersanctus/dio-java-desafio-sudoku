package me.rogerioferreira.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.rogerioferreira.sudoku.Point;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.GameTransitionEvent;
import me.rogerioferreira.sudoku.game.screens.FixedSpaceAssignmentScreen;
import me.rogerioferreira.sudoku.game.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {
  private GameStatus status = GameStatus.UNSTARTED;
  private Board board;

  public static final int GAME_SCREEN_SIZE = 800;
  public static final int SPACE_SIZE = 88;

  public OrthographicCamera camera;
  public ShapeRenderer shapeRenderer;
  public SpriteBatch batch;
  public BitmapFont font;

  private GameState gameState;

  private EventMediator eventMediator = new EventMediator();
  private BoardLogic boardLogic;

  private StartScreen startScreen;
  private FixedSpaceAssignmentScreen fixedSpaceAssignmentScreen;

  public Game() {
    System.out.println("Game created!");

    this.board = new Board(Board.DEFAULT_SIZE);
    this.boardLogic = new BoardLogic(this, this.board, this.eventMediator);

    this.eventMediator.addEventListener(event -> {
      switch (event) {
        case GameTransitionEvent transitionEvent -> {
          this.handleGameTransition(transitionEvent.gameState());
        }
        default -> {
        }
      }
    });
  }

  private void handleGameTransition(GameState gameState) {
    this.gameState = gameState;

    switch (gameState) {
      case GameState.START -> {
        this.setScreen(this.startScreen);
      }
      case GameState.FIXED_SPACE_ASSIGNEMENT -> {
        this.setScreen(this.fixedSpaceAssignmentScreen);
      }
      default -> {
      }
    }
  }

  public GameState getGameState() {
    return this.gameState;
  }

  @Override
  public void create() {
    System.out.println("Game started!");

    this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.camera.setToOrtho(false);
    this.shapeRenderer = new ShapeRenderer();
    this.batch = new SpriteBatch();
    this.font = new BitmapFont();

    font.setColor(0, 0, 0, 1);
    font.getData().setScale(3f, 3f);
    font.setUseIntegerPositions(false);
    font.getRegion().getTexture().setFilter(TextureFilter.Nearest,
        TextureFilter.Nearest);

    this.startScreen = new StartScreen(this, this.eventMediator);
    this.fixedSpaceAssignmentScreen = new FixedSpaceAssignmentScreen(this, this.board, this.eventMediator);

    eventMediator.fireEvent(new GameTransitionEvent(GameState.START));
  }

  @Override
  public void render() {
    this.camera.update();
    var projectionMatrix = this.camera.combined;
    this.batch.setProjectionMatrix(projectionMatrix);
    this.shapeRenderer.setProjectionMatrix(projectionMatrix);

    super.render();
  }

  @Override
  public void dispose() {
    System.out.println("Game closed!");

    this.shapeRenderer.dispose();
    this.font.dispose();
    this.batch.dispose();
    this.startScreen.dispose();
  }
}
