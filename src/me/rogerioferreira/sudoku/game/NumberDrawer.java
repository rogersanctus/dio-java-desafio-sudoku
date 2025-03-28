package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NumberDrawer {
  private List<Texture> normalNumbers = new ArrayList<>();
  private List<Texture> fixedNumbers = new ArrayList<>();
  private List<Texture> invalidNumbers = new ArrayList<>();

  public NumberDrawer() {
    for (int i = 1; i <= 9; i++) {
      this.normalNumbers.add(new Texture("num_" + i + "_normal.png"));
      this.fixedNumbers.add(new Texture("num_" + i + "_fixed.png"));
      this.invalidNumbers.add(new Texture("num_" + i + "_invalid.png"));
    }
  }

  public void draw(SpriteBatch batch, int number, NumberState state, float center_x, float center_y) {
    // batch.begin();

    if (number >= 1 && number <= 9) {
      Texture texture = switch (state) {
        case FIXED ->
          this.fixedNumbers.get(number - 1);
        case INVALID ->
          this.invalidNumbers.get(number - 1);
        case NORMAL ->
          this.normalNumbers.get(number - 1);
        default ->
          this.normalNumbers.get(number - 1);
      };

      batch.draw(texture, center_x - texture.getWidth() * 0.5f, center_y - texture.getHeight() * 0.5f);
    }

    // batch.end();
  }

  public void dispose() {
    for (int i = 0; i < this.normalNumbers.size(); i++) {
      this.normalNumbers.get(i).dispose();
      this.fixedNumbers.get(i).dispose();
      this.invalidNumbers.get(i).dispose();
    }
  }
}
