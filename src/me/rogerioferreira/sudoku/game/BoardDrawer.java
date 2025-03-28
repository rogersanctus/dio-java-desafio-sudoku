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
  private int offset = 0;
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
  }

  public void draw() {
    this.game.batch.begin();

    this.game.batch.draw(this.boardTexture, 0, 0, Game.GAME_SCREEN_SIZE, Game.GAME_SCREEN_SIZE);

    /// Draw the spaces
    for (int x = 0; x < this.board.getSize(); x++) {
      for (int y = 0; y < this.board.getSize(); y++) {
        var space = this.board.getSpace(new Point(x, y));

        if (space != null) {
          // Draw the value
          var value = space.getValue() == null ? "" : space.getValue().toString();

          var spaceX = x * Game.SPACE_SIZE + this.offset + Game.SPACE_SIZE * 0.5f - 11;
          var spaceY = y * Game.SPACE_SIZE + this.offset + Game.SPACE_SIZE * 0.5f
              - this.game.font.getCapHeight() * 0.5f;

          var worldSpace = this.game.camera.unproject(new Vector3(spaceX, spaceY, 0));

          // change de color if the space is not valid
          if (space.getIsValid()) {
            this.game.font.setColor(Color.BLACK);
          } else {
            this.game.font.setColor(Color.RED);
          }

          // draw values
          this.game.font.draw(this.game.batch, value, worldSpace.x, worldSpace.y);
        }
      }
    }
    this.game.batch.end();

    // Draw the mouse position
    Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    var spaceSize = Game.SPACE_SIZE + LINE_WIDTH; // Space size + line width
    var adjustedMouseX = this.mouseXPos * spaceSize + this.offset;
    var adjustedMouseY = this.mouseYPos * spaceSize + this.offset;

    var adjustedMouseWorldPos = this.game.camera.unproject(new Vector3(adjustedMouseX, adjustedMouseY, 0));

    this.game.shapeRenderer.setColor(this.MouseSpacePositionColor);
    this.game.shapeRenderer.rect(adjustedMouseWorldPos.x, adjustedMouseWorldPos.y - Game.SPACE_SIZE,
        Game.SPACE_SIZE,
        Game.SPACE_SIZE);

    this.game.shapeRenderer.end();
  }

  public void setMouseRectColor(Color color) {
    this.MouseSpacePositionColor = color;
  }

  public void dispose() {
    this.boardTexture.dispose();
  }
}
