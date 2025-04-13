package ubx.archilog.model.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ubx.archilog.model.Color;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Position;
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
  private boolean borders;

  public Group(boolean borders) {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
    this.zIndex = 1;
    this.borders = borders;
  }

  public Group(final int zIndex, boolean borders) {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
    this.zIndex = zIndex;
    this.borders = borders;
  }

  public void updateChildZIndex() {
    for (final Shape shape : shapesList) {
      shape.setZindex(0);
    }
  }

  public void add(final Shape shape) {
    if (!shapesSet.contains(shape)) {
      shapesSet.add(shape);
      shapesList.add(shape);
      setCorner();
      setSize();
    }
  }

  public void remove(final Shape shape) {
    shapesSet.remove(shape);
    shapesList.remove(shape);
    setCorner();
    setSize();
  }

  public List<Shape> getShapes() {
    return shapesList;
  }

  private void setCorner() {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    for (final Shape shape : shapesList) {
      if (shape.getX() < minX) {
        minX = shape.getX();
      }
      if (shape.getY() < minY) {
        minY = shape.getY();
      }
    }
    this.x = minX;
    this.y = minY;
  }

  private void setSize() {
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    for (final Shape shape : shapesList) {
      if (shape.getX() + shape.getWidth() > maxX) {
        maxX = shape.getX() + shape.getWidth();
      }
      if (shape.getY() + shape.getHeight() > maxY) {
        maxY = shape.getY() + shape.getHeight();
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
  public void setX(final int x) {
    this.x = x;
  }

  @Override
  public void setY(final int y) {
    this.y = y;
  }

  @Override
  public void setColor(final Color color) {}

  @Override
  public int getZindex() {
    return zIndex;
  }

  @Override
  public void setZindex(final int z) {
    this.zIndex = z;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public void setWidth(final int width) {
    this.width = width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void setHeight(final int height) {
    this.height = height;
  }

  @Override
  public void moveTo(final Position pos) {
    final int diffX = pos.x() - x;
    final int diffY = pos.y() - y;
    for (final Shape shape : shapesList) {
      shape.translate(diffX, diffY);
    }
    setCorner();
  }

  @Override
  public void draw(final Render render) {
    for (final Shape shape : shapesList) {
      shape.draw(render);
    }
    if (borders) {
      render.drawRect(x, y, width, height, false, new Color(0, 0, 0, 255));
    }
  }

  @Override
  public void scale(final float factor) {
    for (final Shape shape : shapesList) {
      shape.scale(factor);
      shape.moveTo(
          new Position(
              (int) (x + (shape.getX() - x) * factor), (int) (y + (shape.getY() - y) * factor)));
    }
    setCorner();
    setSize();
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException("Rotation not supported on Group");
  }

  @Override
  public void translate(final int xDiff, final int yDiff) {
    for (final Shape shape : shapesList) {
      shape.translate(xDiff, yDiff);
    }
    setCorner();
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    final Group copy = new Group(false);
    for (final Shape shape : shapesList) {
      copy.add(shape.clone());
    }
    return copy;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Group o)) return false;
    return shapesList.equals(o.shapesList);
  }

  @Override
  public int hashCode() {
    int result = 1;
    for (final Shape shape : shapesList) {
      result = 31 * result + (shape == null ? 0 : shape.hashCode());
    }
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder
        .append("Group x=")
        .append(x)
        .append(", y=")
        .append(y)
        .append(", zIndex=")
        .append(zIndex)
        .append(" [");
    for (final Shape s : shapesList) {
      stringBuilder.append(s.toString());
      stringBuilder.append(" ");
    }
    stringBuilder.append("]");
    return stringBuilder.toString();
  }

  @Override
  public Memento save() {
    return new GroupMemento(this);
  }

  private class GroupMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final List<Memento> shapesMemento;
    private final List<Shape> shapeRefs;
    private final Group originator;

    public GroupMemento(final Group group) {
      this.originator = group;
      this.x = group.getX();
      this.y = group.getY();
      this.zIndex = group.getZindex();
      this.width = group.getWidth();
      this.height = group.getHeight();
      this.shapesMemento = new ArrayList<>();
      this.shapeRefs = new ArrayList<>();

      for (final Shape shape : group.getShapes()) {
        shapesMemento.add(shape.save());
        shapeRefs.add(shape);
      }
    }

    public void restore() {
      originator.setX(x);
      originator.setY(y);
      originator.setZindex(zIndex);
      originator.setWidth(width);
      originator.setHeight(height);

      originator.shapesList.clear();
      originator.shapesSet.clear();
      for (int i = 0; i < shapeRefs.size(); i++) {
        originator.shapesList.add(shapeRefs.get(i));
        originator.shapesSet.add(shapeRefs.get(i));
        shapesMemento.get(i).restore();
      }
    }
  }
}
