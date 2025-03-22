package me.rogerioferreira.sudoku.events;

import java.util.ArrayList;
import java.util.List;

public class EventMediator {
  private List<EventListener> events = new ArrayList<>();

  public void addEventListener(EventListener listener) {
    this.events.add(listener);
  }

  public void fireEvent(Event event) {
    for (EventListener listener : this.events) {
      listener.onEvent(event);
    }
  }
}
