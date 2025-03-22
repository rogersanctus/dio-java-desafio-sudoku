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
import me.rogerioferreira.sudoku.game.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {
  private GameStatus status = GameStatus.UNSTARTED;
  private Board board;

  public static final int GAME_SCREEN_SIZE = 800;
  private static final int SPACE_SIZE = 88;
  private int offset = 0;

  public OrthographicCamera camera;
  public ShapeRenderer shapeRenderer;
  public SpriteBatch batch;
  public BitmapFont font;

  private EventMediator eventMediator = new EventMediator();

  private StartScreen startScreen;
  // private RunningScreen runningScreen;
  // private GameCompletedScreen gameCompletedScreen;

  public Game() {
    System.out.println("Game created!");
    this.board = new Board(Board.DEFAULT_SIZE);

    this.offset = GAME_SCREEN_SIZE - SPACE_SIZE * this.board.getSize();
    this.offset /= 2;

  }

  private void renderBoard() {
    var mouseX = Gdx.input.getX();
    var mouseY = Gdx.input.getY();

    final var lineColor = new Color(0.4f, 0.4f, 0.4f, 1);
    final var regionColor = new Color(0, 0.6f, 0.9f, 1);

    /// Board background
    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    shapeRenderer.setColor(1, 1, 1, 1);
    shapeRenderer.rect(this.offset, this.offset, GAME_SCREEN_SIZE - this.offset * 2,
        GAME_SCREEN_SIZE - this.offset * 2);
    shapeRenderer.end();

    /// Board lines
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // Para contorno
    // shapeRenderer.setColor(0.4f, 0.4f, 0.4f, 1);

    for (int x = 0; x <= this.board.getSize(); x++) {
      var xpos = x * SPACE_SIZE + this.offset;

      if (x % this.board.getRegionSize() == 0) {
        shapeRenderer.setColor(regionColor);

        shapeRenderer.line(xpos - 1, this.offset, xpos - 1, GAME_SCREEN_SIZE - this.offset);
        shapeRenderer.line(xpos + 1, this.offset, xpos + 1, GAME_SCREEN_SIZE - this.offset);
      } else {
        shapeRenderer.setColor(lineColor);
      }

      shapeRenderer.line(xpos, this.offset, xpos, GAME_SCREEN_SIZE - this.offset);
    }

    for (int y = 0; y <= this.board.getSize(); y++) {
      var ypos = y * SPACE_SIZE + this.offset;

      if (y % this.board.getRegionSize() == 0) {
        shapeRenderer.setColor(regionColor);
        shapeRenderer.line(this.offset, ypos - 1, GAME_SCREEN_SIZE - this.offset, ypos - 1);
        shapeRenderer.line(this.offset, ypos + 1, GAME_SCREEN_SIZE - this.offset, ypos + 1);
      } else {
        shapeRenderer.setColor(lineColor);
      }

      shapeRenderer.line(this.offset, ypos, GAME_SCREEN_SIZE - this.offset, ypos);
    }

    shapeRenderer.end();

    /// Draw the space values
    this.batch.begin();
    for (int x = 0; x < this.board.getSize(); x++) {
      for (int y = 0; y < this.board.getSize(); y++) {
        var space = this.board.getSpace(new Point(x, y));

        if (space != null) {
          // Draw the value
          var value = space.getValue() == null ? "" : space.getValue().toString();

          this.font.draw(this.batch, value, x * SPACE_SIZE + this.offset + SPACE_SIZE * 0.5f - 11,
              y * SPACE_SIZE + this.offset + SPACE_SIZE * 0.5f + font.getCapHeight() * 0.5f);

        }
      }
    }
    this.batch.end();

    Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    shapeRenderer.setColor(1, 0, 0, 0.1f);
    shapeRenderer.rect((int) (mouseX / SPACE_SIZE) * SPACE_SIZE + this.offset,
        (int) (mouseY / SPACE_SIZE) * SPACE_SIZE + this.offset, SPACE_SIZE, SPACE_SIZE);

    shapeRenderer.end();

  }

  private void renderStartScreen() {

  }

  private void renderRunningScreen() {

  }

  private void renderGameCompletedScreen() {

  }

  @Override
  public void create() {
    System.out.println("Game started!");

    // for (int y = 0, numOffset = 1; y < this.board.getSize(); y++) {
    // for (int x = 0; x < this.board.getSize(); x++) {
    // board.assignSpace(false, new Point(x, y), numOffset % (this.board.getSize() +
    // 1));
    // numOffset++;
    // }
    // numOffset = y + 2;
    // }

    this.camera = new OrthographicCamera();
    this.camera.setToOrtho(true);
    this.shapeRenderer = new ShapeRenderer();
    this.batch = new SpriteBatch();
    this.font = new BitmapFont();

    font.setColor(0, 0, 0, 1);
    font.getData().setScale(3f, -3f);
    font.setUseIntegerPositions(false);
    font.getRegion().getTexture().setFilter(TextureFilter.Nearest,
        TextureFilter.Nearest);

    this.startScreen = new StartScreen(this, this.eventMediator);
    this.setScreen(this.startScreen);

  }

  @Override
  public void render() {
    // Gdx.gl.glClearColor(0, 0, 0, 1);
    // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    super.render();

    this.camera.update();
    this.batch.setProjectionMatrix(this.camera.combined);
    this.shapeRenderer.setProjectionMatrix(this.camera.combined);

    // this.renderBoard();
    //
    // switch (this.status) {
    // case GameStatus.INCOMPLETED:
    // renderRunningScreen();
    // break;
    // case GameStatus.COMPLETED:
    // renderGameCompletedScreen();
    // break;
    // case GameStatus.UNSTARTED:
    // default:
    // renderStartScreen();
    // break;
    // }
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
