package ubx.archilog.controller;

import ubx.archilog.model.Observer;

public interface Observable {
  void addObserver(Observer obs);

  void removeObserver(Observer obs);

  void notifyObservers();
}
