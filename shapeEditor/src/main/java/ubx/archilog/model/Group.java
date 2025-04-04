package ubx.archilog.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Group implements Shape {
  private final Set<Shape> shapesSet;
  private final List<Shape> shapesList;
  int x;
  int y;

  public Group() {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
  }

  public void add(Shape s) {
    if (!shapesSet.contains(s)) {
      shapesSet.add(s);
      shapesList.add(s);
      setCorner();
    }
  }

  public void remove(Shape s) {
    shapesSet.remove(s);
    shapesList.remove(s);
    setCorner();
  }

  public List<Shape> getShapes() {
    return shapesList;
  }

  private void setCorner() {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    for (Shape s : shapesList) {
      if (s.getX() < minX) {
        minX = s.getX();
      }
      if (s.getY() < minY) {
        minY = s.getY();
      }
    }
    this.x = minX;
    this.y = minY;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  @Override
  public void draw(Render render) {
    for (Shape s : shapesList) {
      s.draw(render);
    }
  }

  @Override
  public void scale() {}

  @Override
  public void rotate() {
    throw new UnsupportedOperationException("Rotation not supported on Group");
  }

  @Override
  public void translate(int xDiff, int yDiff) {
    for (Shape s : shapesList) {
      s.translate(xDiff, yDiff);
    }
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    Group copy = new Group();
    List<Shape> clonedShapes = List.copyOf(shapesList);
    for (Shape s : clonedShapes) {
      copy.add(s);
    }
    return copy;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Group o)) return false;
    return shapesList.equals(o.shapesList);
  }

  @Override
  public int hashCode() {
    int result = 1;
    for (Shape shape : shapesList) {
      result = 31 * result + (shape == null ? 0 : shape.hashCode());
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Group x=" + x + ", y=" + y + " [");
    for (Shape s : shapesList) {
      sb.append(s.toString());
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();
  }
}
