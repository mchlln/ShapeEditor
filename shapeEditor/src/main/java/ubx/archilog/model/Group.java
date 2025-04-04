package ubx.archilog.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Group extends AbstractShape {
  private final Set<Shape> shapesSet;
  private final ArrayList<Shape> shapesList;
  private int width;
  private int height;

  public Group(int x, int y, int width, int height) {
    super(x, y);
    this.width = width;
    this.height = height;
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

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException("Rotation not supported on Group");
  }

  @Override
  public void draw(Render render) {
    for (Shape s : shapesList) {
      s.draw(render);
    }
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
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Group [");
    for (Shape s : shapesList) {
      sb.append(s.toString());
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();
  }
}
