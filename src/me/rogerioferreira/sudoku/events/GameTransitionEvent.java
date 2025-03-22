package me.rogerioferreira.sudoku.events;

import me.rogerioferreira.sudoku.game.GameState;

public record GameTransitionEvent(GameState gameState) implements Event {
}
