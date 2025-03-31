package ubx.archilog.model;

public interface Observable {
  void addObserver(ShapeObserver obs);

  void removeObserver(ShapeObserver obs);

  void notifyObservers();
}
