package me.rogerioferreira.sudoku.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import me.rogerioferreira.sudoku.Point;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.MouseMoveEvent;

public class BoardDrawer {
  private Game game;
  private Board board;
  private EventMediator eventMediator;

  private Texture boardTexture;
  private Texture titleAreaTexture;
  private Texture gameCompletedTextTexture;
  private Texture gameCompletedCelebrationPoppers;
  private NumberDrawer numberDrawer;
  private Point offset = Game.GLOBAL_OFFSET;
  private final int LINE_WIDTH = 1;

  private int mouseXPos = 0;
  private int mouseYPos = 0;
  private Color MouseSpacePositionColor = new Color(1, 0, 0, 0.1f);

  public BoardDrawer(Game game, Board board, EventMediator eventMediator) {
    this.game = game;
    this.board = board;
    this.eventMediator = eventMediator;

    this.eventMediator.addEventListener(event -> {
      if (event instanceof MouseMoveEvent mouseMoveEvent) {
        this.mouseXPos = mouseMoveEvent.x();
        this.mouseYPos = mouseMoveEvent.y();
      }
    });

    this.boardTexture = new Texture("sudoku_board.png");
    this.titleAreaTexture = new Texture("title_area.png");
    this.gameCompletedTextTexture = new Texture("game_completed_text.png");
    this.gameCompletedCelebrationPoppers = new Texture("game_completed_celebration_poppers.png");

    this.boardTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    this.gameCompletedTextTexture.setWrap(Texture.TextureWrap.ClampToEdge, Texture.TextureWrap.ClampToEdge);
    this.gameCompletedTextTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

    this.numberDrawer = new NumberDrawer();
  }

  public void draw() {
    this.game.batch.begin();

    this.game.batch.draw(this.titleAreaTexture, 0, Game.GAME_SCREEN_SIZE,
        this.titleAreaTexture.getWidth(),
        this.titleAreaTexture.getHeight());

    this.game.batch.draw(
        this.boardTexture,
        0, 0,
        this.boardTexture.getWidth(),
        this.boardTexture.getHeight());

    /// Draw the spaces
    for (int x = 0; x < this.board.getSize(); x++) {
      for (int y = 0; y < this.board.getSize(); y++) {
        var space = this.board.getSpace(new Point(x, y));

        if (space != null) {
          // Draw the value
          var spaceX = x * Game.SPACE_SIZE + this.offset.x() + Game.SPACE_SIZE * 0.5f;
          var spaceY = y * Game.SPACE_SIZE + this.offset.y() + Game.SPACE_SIZE * 0.5f;
          var worldSpace = this.game.camera.unproject(new Vector3(spaceX, spaceY, 0));

          var intValue = space.getValue() == null ? 0 : space.getValue();

          NumberState state = NumberState.NORMAL;

          if (space.getIsFixed()) {
            state = NumberState.FIXED;
          }

          if (!space.getIsValid()) {
            state = NumberState.INVALID;
          }

          this.numberDrawer.draw(this.game.batch, intValue, state, worldSpace.x, worldSpace.y);
        }
      }
    }
    this.game.batch.end();

    // Draw the mouse position
    Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    if (this.game.getGameState() != GameState.COMPLETED) {
      this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

      var spaceSize = Game.SPACE_SIZE + LINE_WIDTH; // Space size + line width
      var adjustedMouseX = this.mouseXPos * spaceSize + this.offset.x();
      var adjustedMouseY = this.mouseYPos * spaceSize + this.offset.y();

      var adjustedMouseWorldPos = this.game.camera.unproject(new Vector3(adjustedMouseX, adjustedMouseY, 0));

      this.game.shapeRenderer.setColor(this.MouseSpacePositionColor);
      this.game.shapeRenderer.rect(adjustedMouseWorldPos.x, adjustedMouseWorldPos.y - Game.SPACE_SIZE,
          Game.SPACE_SIZE,
          Game.SPACE_SIZE);

      this.game.shapeRenderer.end();
    }

    if (this.game.getGameState() == GameState.COMPLETED) {
      this.game.batch.begin();

      this.game.batch.draw(this.gameCompletedTextTexture,
          Game.GAME_SCREEN_WIDTH * 0.5f - this.gameCompletedTextTexture.getWidth() * 0.5f,
          Game.GAME_SCREEN_HEIGHT * 0.5f - this.gameCompletedTextTexture.getHeight() * 0.5f,
          this.gameCompletedTextTexture.getWidth(),
          this.gameCompletedTextTexture.getHeight());

      this.game.batch.draw(this.gameCompletedCelebrationPoppers,
          Game.GAME_SCREEN_WIDTH * 0.5f - this.gameCompletedCelebrationPoppers.getWidth() * 0.5f,
          100,
          this.gameCompletedCelebrationPoppers.getWidth(),
          this.gameCompletedCelebrationPoppers.getHeight());

      this.game.batch.end();
    }
  }

  public void setMouseRectColor(Color color) {
    this.MouseSpacePositionColor = color;
  }

  public void dispose() {
    this.boardTexture.dispose();
    this.titleAreaTexture.dispose();
    this.gameCompletedTextTexture.dispose();
    this.gameCompletedCelebrationPoppers.dispose();
    this.numberDrawer.dispose();
  }
}
