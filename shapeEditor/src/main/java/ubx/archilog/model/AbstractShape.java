package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public abstract class AbstractShape implements Shape {
  private int x;
  private int y;
  private int zIndex;
  private int width;
  private int height;
  private Color color = new Color(245, 40, 145, 255);

  public AbstractShape(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final Color color) {
    this.x = x;
    this.y = y;
    this.zIndex = zIndex;
    this.width = width;
    this.height = height;
    this.color = color;
  }

  public AbstractShape(
      final int x, final int y, final int zIndex, final int width, final int height) {
    this.x = x;
    this.y = y;
    this.zIndex = zIndex;
    this.width = width;
    this.height = height;
  }

  @Override
  public void draw(final Render render) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void scale(final float factor) {
    width *= factor;
    height *= factor;
  }

  @Override
  public void translate(final int xDiff, final int yDiff) {
    x += xDiff;
    y += yDiff;
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
  public void setColor(final Color color) {
    this.color = color;
  }

  @Override
  public void setY(final int y) {
    this.y = y;
  }

  @Override
  public int getZindex() {
    return zIndex;
  }

  @Override
  public void setZindex(final int zIndex) {
    this.zIndex = zIndex;
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
    this.x = pos.x();
    this.y = pos.y();
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof AbstractShape) {
      return this.x == ((AbstractShape) obj).x
          && this.y == ((AbstractShape) obj).y
          && this.color == ((AbstractShape) obj).color;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + x;
    result = 31 * result + y;
    result = 31 * result + color.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return this.getClass()
        + " [x="
        + x
        + ", y="
        + y
        + ", zIndex="
        + zIndex
        + ", color="
        + color
        + "]";
  }

  public Color getColor() {
    return color;
  }

  @Override
  public Memento save() {
    return new AbstractMemento(this);
  }

  private class AbstractMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final Color color;
    private final Shape originator;

    public AbstractMemento(final AbstractShape s) {
      this.originator = s;
      this.x = s.getX();
      this.y = s.getY();
      this.zIndex = s.getZindex();
      this.width = s.getWidth();
      this.height = s.getHeight();
      this.color = s.getColor();
    }

    public void restore() {
      originator.setX(x);
      originator.setY(y);
      originator.setZindex(zIndex);
      originator.setWidth(width);
      originator.setHeight(height);
      originator.setColor(color);
    }
  }
}
