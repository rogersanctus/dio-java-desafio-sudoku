package me.rogerioferreira.sudoku.game;

import me.rogerioferreira.sudoku.events.ClearSpaceEvent;
import me.rogerioferreira.sudoku.events.Event;
import me.rogerioferreira.sudoku.events.EventMediator;
import me.rogerioferreira.sudoku.events.SpaceAssignmentEvent;

public class BoardLogic {
  private EventMediator eventMediator;
  private Game game;
  private Board board;
  private boolean isInitial = true;

  public BoardLogic(Game game, Board board, EventMediator mediator) {
    this.game = game;
    this.board = board;
    this.eventMediator = mediator;

    this.eventMediator.addEventListener(event -> this.handleEvent(event));
  }

  private void handleEvent(Event event) {
    if (game.getGameState() != GameState.FIXED_SPACE_ASSIGNEMENT && game.getGameState() != GameState.PLAYING) {
      return;
    }

    switch (event) {
      case SpaceAssignmentEvent spaceAssignmentEvent -> {
        this.board.assignSpace(this.isInitial, spaceAssignmentEvent.point(),
            spaceAssignmentEvent.value());
      }
      case ClearSpaceEvent clearSpaceEvent -> {
        this.board.clearSpace(clearSpaceEvent.point());
      }
      default -> {
      }
    }
  }
}
