package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

import me.rogerioferreira.sudoku.Point;

public class Board {
  static final int DEFAULT_SIZE = 9;
  private int size;
  int regionSize;

  private List<List<Space>> spaces;

  public Board(int size) {
    this.size = size;
    this.regionSize = this.computeRegionSize();

    this.createSpaces();
  }

  private int computeRegionSize() {
    return (int) Math.sqrt(size);
  }

  private void createSpaces() {
    this.spaces = new ArrayList<>(this.size);

    for (int i = 0; i < this.size; i++) {
      this.spaces.add(new ArrayList<>(this.size));

      for (int j = 0; j < this.size; j++) {
        this.spaces.get(i).add(new Space());
      }
    }
  }

  public void assignSpace(boolean isInitial, Point point, int value) {
    var column = this.spaces.get(point.x());
    var space = column.get(point.y());

    space.isFixed = isInitial;
    space.value = value;
  }

  public int getSize() {
    return this.size;
  }

  public int getRegionSize() {
    return this.regionSize;
  }

  public Space getSpace(Point point) {
    if (point.x() < 0 || point.x() >= this.size || point.y() < 0 || point.y() >= this.size) {
      return null;
    }

    return this.spaces.get(point.x()).get(point.y());
  }
}
