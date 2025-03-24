package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

import me.rogerioferreira.sudoku.Point;

public class Space {
  boolean isFixed;
  boolean isValid;
  Integer value;
  Point point;
  SpaceRegion region;

  private List<Integer> candidateValues = new ArrayList<>();

  public Space() {
    this.limpar();
  }

  public void limpar() {
    this.isFixed = false;
    this.isValid = true;
    this.value = null;
  }

  public boolean getIsFixed() {
    return this.isFixed;
  }

  public boolean getIsValid() {
    return this.isValid;
  }

  public Integer getValue() {
    return this.value;
  }

}
