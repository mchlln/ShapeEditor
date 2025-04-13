package ubx.archilog.model.shapes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ubx.archilog.model.Color;
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
      shape.zIndex(0);
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

  public List<Shape> shapes() {
    return shapesList;
  }

  private void setCorner() {
    int minX = Integer.MAX_VALUE;
    int minY = Integer.MAX_VALUE;
    for (final Shape shape : shapesList) {
      if (shape.x() < minX) {
        minX = shape.x();
      }
      if (shape.y() < minY) {
        minY = shape.y();
      }
    }
    this.x = minX;
    this.y = minY;
  }

  private void setSize() {
    int maxX = Integer.MIN_VALUE;
    int maxY = Integer.MIN_VALUE;
    for (final Shape shape : shapesList) {
      if (shape.x() + shape.width() > maxX) {
        maxX = shape.x() + shape.width();
      }
      if (shape.y() + shape.height() > maxY) {
        maxY = shape.y() + shape.height();
      }
    }
    this.width = maxX - this.x;
    this.height = maxY - this.y;
  }

  @Override
  public int x() {
    return x;
  }

  @Override
  public int y() {
    return y;
  }

  @Override
  public void x(final int x) {
    this.x = x;
  }

  @Override
  public void y(final int y) {
    this.y = y;
  }

  @Override
  public Color color() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void color(final Color color) {}

  @Override
  public int zIndex() {
    return zIndex;
  }

  @Override
  public void zIndex(final int z) {
    this.zIndex = z;
  }

  @Override
  public int width() {
    return width;
  }

  @Override
  public void width(final int width) {
    this.width = width;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public void height(final int height) {
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
          new Position((int) (x + (shape.x() - x) * factor), (int) (y + (shape.y() - y) * factor)));
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
      this.x = group.x();
      this.y = group.y();
      this.zIndex = group.zIndex();
      this.width = group.width();
      this.height = group.height();
      this.shapesMemento = new ArrayList<>();
      this.shapeRefs = new ArrayList<>();

      for (final Shape shape : group.shapes()) {
        shapesMemento.add(shape.save());
        shapeRefs.add(shape);
      }
    }

    public void restore() {
      originator.x(x);
      originator.y(y);
      originator.zIndex(zIndex);
      originator.width(width);
      originator.height(height);

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
