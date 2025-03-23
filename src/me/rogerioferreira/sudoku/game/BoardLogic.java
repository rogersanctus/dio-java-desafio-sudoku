package me.rogerioferreira.sudoku.game;

import me.rogerioferreira.sudoku.events.Event;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.SpaceAssignmentEvent;

public class BoardLogic {
  private EventMediator eventMediator;
  private Board board;
  private boolean isInitial = true;

  public BoardLogic(EventMediator mediator, Board board) {
    this.board = board;

    this.eventMediator = mediator;

    this.eventMediator.addEventListener(event -> this.handleEvent(event));
  }

  private void handleEvent(Event event) {
    switch (event) {
      case SpaceAssignmentEvent spaceAssignmentEvent -> {
        this.board.assignSpace(this.isInitial, spaceAssignmentEvent.point(),
            spaceAssignmentEvent.value());
      }
      default -> {
      }
    }
  }
}
