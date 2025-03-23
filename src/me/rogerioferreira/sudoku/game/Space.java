package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

public class Space {
  boolean isFixed = false;
  boolean isValid = true;
  Integer value = null;
  SpaceRegion region;

  private List<Integer> candidateValues = new ArrayList<>();

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
