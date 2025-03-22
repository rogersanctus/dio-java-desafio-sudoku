package me.rogerioferreira.sudoku.game;

public enum GameStatus {
  UNSTARTED("não iniciado"),
  INCOMPLETED("incompleto"),
  COMPLETED("completo");

  private String description;

  GameStatus(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.description;
  }
}
