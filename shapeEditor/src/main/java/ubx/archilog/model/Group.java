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

  public Group(final int zIndex) {
    this.shapesSet = new HashSet<>();
    this.shapesList = new ArrayList<>();
    this.zIndex = zIndex;
  }

  public void updateChildZIndex() {
    for (final Shape s : shapesList) {
      s.setZindex(0);
    }
  }

  public void add(final Shape s) {
    if (!shapesSet.contains(s)) {
      shapesSet.add(s);
      shapesList.add(s);
      setCorner();
      setSize();
    }
  }

  public void remove(final Shape s) {
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
    for (final Shape s : shapesList) {
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
    for (final Shape s : shapesList) {
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
    for (final Shape s : shapesList) {
      s.translate(diffX, diffY);
    }
    setCorner();
  }

  @Override
  public void draw(final Render render) {
    for (final Shape s : shapesList) {
      s.draw(render);
    }
    render.drawRect(x, y, width, height, false, new Color(0, 0, 0, 255));
  }

  @Override
  public void scale(final float factor) {
    for (final Shape s : shapesList) {
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
  public void translate(final int xDiff, final int yDiff) {
    for (final Shape s : shapesList) {
      s.translate(xDiff, yDiff);
    }
    setCorner();
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    final Group copy = new Group();
    for (final Shape s : shapesList) {
      copy.add(s.clone());
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
    final StringBuilder sb = new StringBuilder();
    sb.append("Group x=")
        .append(x)
        .append(", y=")
        .append(y)
        .append(", zIndex=")
        .append(zIndex)
        .append(" [");
    for (final Shape s : shapesList) {
      sb.append(s.toString());
      sb.append(" ");
    }
    sb.append("]");
    return sb.toString();
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

    public GroupMemento(final Group s) {
      this.originator = s;
      this.x = s.getX();
      this.y = s.getY();
      this.zIndex = s.getZindex();
      this.width = s.getWidth();
      this.height = s.getHeight();
      this.shapesMemento = new ArrayList<>();
      this.shapeRefs = new ArrayList<>();

      for (final Shape shape : s.getShapes()) {
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
