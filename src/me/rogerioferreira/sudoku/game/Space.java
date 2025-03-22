package me.rogerioferreira.sudoku.game;

import java.util.ArrayList;
import java.util.List;

public class Space {
  boolean isFixed = false;
  Integer value = null;

  private List<Integer> candidateValues = new ArrayList<>();

  public boolean getIsFixed() {
    return this.isFixed;
  }

  public Integer getValue() {
    return this.value;
  }

}
