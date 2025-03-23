package me.rogerioferreira.sudoku.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import me.rogerioferreira.sudoku.Point;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.game.Board;
import me.rogerioferreira.sudoku.game.Game;

public class FixedSpaceAssignmentScreen implements Screen {
  private Game game;
  private Board board;
  private EventMediator eventMediator;

  private int offset = 0;
  private final int LINE_WIDTH = 1;

  private Texture boardTexture;

  public FixedSpaceAssignmentScreen(Game game, Board board, EventMediator eventMediator) {
    this.game = game;
    this.board = board;
    this.eventMediator = eventMediator;

    var linesBetweenSpaces = this.board.getSize() - 1;
    this.offset = Game.GAME_SCREEN_SIZE - Game.SPACE_SIZE * this.board.getSize() - linesBetweenSpaces * LINE_WIDTH;
    this.offset /= 2;

    this.boardTexture = new Texture("sudoku_board.png");
  }

  private void draw() {
    var mouseX = Gdx.input.getX();
    var mouseY = Gdx.input.getY();

    /// Draw the space values
    this.game.batch.begin();

    /// Draw the board
    this.game.batch.draw(this.boardTexture, 0, 0, Game.GAME_SCREEN_SIZE, Game.GAME_SCREEN_SIZE);

    /// Draw the values in the spaces
    for (int x = 0; x < this.board.getSize(); x++) {
      for (int y = 0; y < this.board.getSize(); y++) {
        var space = this.board.getSpace(new Point(x, y));

        if (space != null) {
          // Draw the value
          var value = space.getValue() == null ? "" : space.getValue().toString();

          this.game.font.draw(this.game.batch, value, x * Game.SPACE_SIZE + this.offset + Game.SPACE_SIZE * 0.5f - 11,
              y * Game.SPACE_SIZE + this.offset + Game.SPACE_SIZE * 0.5f + this.game.font.getCapHeight() * 0.5f);

        }
      }
    }
    this.game.batch.end();

    Gdx.gl.glEnable(GL20.GL_BLEND); // Enable blending for transparency
    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

    this.game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

    var spaceSize = Game.SPACE_SIZE + LINE_WIDTH; // Space size + line width
    var adjustedMouseX = (int) (mouseX / spaceSize) * spaceSize + this.offset;
    var adjustedMouseY = (int) (mouseY / spaceSize) * spaceSize + this.offset;

    var adjustedMouseWorldPos = this.game.camera.unproject(new Vector3(adjustedMouseX, adjustedMouseY, 0));

    this.game.shapeRenderer.setColor(1, 0, 0, 0.1f);
    this.game.shapeRenderer.rect(adjustedMouseWorldPos.x, adjustedMouseWorldPos.y - Game.SPACE_SIZE,
        Game.SPACE_SIZE,
        Game.SPACE_SIZE);

    this.game.shapeRenderer.end();

  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {
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
  }
}
