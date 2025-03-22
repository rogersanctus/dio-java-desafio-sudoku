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

  public Game() {
    System.out.println("Game created!");
    this.board = new Board(Board.DEFAULT_SIZE);

    this.offset = GAME_SCREEN_SIZE - SPACE_SIZE * this.board.getSize();
    this.offset /= 2;

    this.eventMediator.addEventListener(event -> {
      switch (event) {
        case GameTransitionEvent transitionEvent -> {
          if (transitionEvent.gameState() == GameState.PLAYING) {
            System.out.println("Game started!");
          }
        }
        default -> {
        }
      }
    });
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

  @Override
  public void create() {
    System.out.println("Game started!");

    this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    this.camera.setToOrtho(false);
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
