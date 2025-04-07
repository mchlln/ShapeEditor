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
  private int x;
  private int y;
  private int zIndex;
  private int width;
  private int height;

  public Group() {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
    this.zIndex = 1;
  }

  public Group(int zIndex) {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
    this.zIndex = zIndex;
  }

  public void updateChildZIndex() {
    for (Shape s : shapesList) {
      s.setZindex(0);
    }
  }

  public void add(Shape s) {
    if (!shapesSet.contains(s)) {
      shapesSet.add(s);
      shapesList.add(s);
      setCorner();
      setSize();
    }
  }

  public void remove(Shape s) {
    shapesSet.remove(s);
    shapesList.remove(s);
    setCorner();
    setSize();
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

  private void setSize() {
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    for (Shape s : shapesList) {
      if (s.getX() + s.getWidth() > maxX) {
        maxX = s.getX() + s.getWidth();
      }
      if (s.getY() + s.getHeight() > maxY) {
        maxY = s.getY() + s.getHeight();
      }
    }
    this.width = maxX - this.x;
    this.height = maxY - this.y;
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
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void setColor(Color color) {}

  @Override
  public int getZindex() {
    return zIndex;
  }

  @Override
  public void setZindex(int z) {
    this.zIndex = z;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public void setWidth(int width) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void setHeight(int height) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void moveTo(Position pos) {
    int diffX = pos.x() - x;
    int diffY = pos.y() - y;
    for (Shape s : shapesList) {
      s.translate(diffX, diffY);
    }
    setCorner();
  }

  @Override
  public void draw(Render render) {
    for (Shape s : shapesList) {
      s.draw(render);
    }
    render.drawRect(
        x,
        y,
        width,
        height,
        false,
        new Color(
            (int) (Math.random() * 256),
            (int) (Math.random() * 256),
            (int) (Math.random() * 256),
            255));
  }

  @Override
  public void scale(float factor) {
    for (Shape s : shapesList) {
      s.scale(factor);
      s.moveTo(
          new Position((int) (x + (s.getX() - x) * factor), (int) (y + (s.getY() - y) * factor)));
    }
    setCorner();
    setSize();
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException("Rotation not supported on Group");
  }

  @Override
  public void translate(int xDiff, int yDiff) {
    for (Shape s : shapesList) {
      s.translate(xDiff, yDiff);
    }
    setCorner();
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    Group copy = new Group();
    for (Shape s : shapesList) {
      copy.add(s.clone());
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
    sb.append("Group x=")
        .append(x)
        .append(", y=")
        .append(y)
        .append(", zIndex=")
        .append(zIndex)
        .append(" [");
    for (Shape s : shapesList) {
      sb.append(s.toString());
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();
  }
}
