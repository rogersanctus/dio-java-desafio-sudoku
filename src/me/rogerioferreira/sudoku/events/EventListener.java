package me.rogerioferreira.sudoku.events;

@FunctionalInterface
public interface EventListener {
  void onEvent(Event event);
}
