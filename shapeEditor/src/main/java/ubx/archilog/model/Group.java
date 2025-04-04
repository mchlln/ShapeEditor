package ubx.archilog.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Group extends AbstractShape {
  private final Set<Shape> shapesSet;
  private final ArrayList<Shape> shapesList;

  public Group() {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
  }

  public void add(Shape s) {
    if (!shapesSet.contains(s)) {
      shapesSet.add(s);
      shapesList.add(s);
    }
  }

  public void remove(Shape s) {
    shapesSet.remove(s);
    shapesList.remove(s);
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException("Rotation not supported on Group");
  }
}
