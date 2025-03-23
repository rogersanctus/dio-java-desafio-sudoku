package me.rogerioferreira.sudoku.events;

import me.rogerioferreira.sudoku.Point;

public record SpaceAssignmentEvent(Point point, Integer value) implements Event {
}
