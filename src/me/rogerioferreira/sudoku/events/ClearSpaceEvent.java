package me.rogerioferreira.sudoku.events;

import me.rogerioferreira.sudoku.Point;

public record ClearSpaceEvent(Point point) implements Event {
}
