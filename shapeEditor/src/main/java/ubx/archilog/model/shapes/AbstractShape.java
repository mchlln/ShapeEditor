package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Position;
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
  public void color(final Color color) {
    this.color = color;
  }

  @Override
  public void y(final int y) {
    this.y = y;
  }

  @Override
  public int zIndex() {
    return zIndex;
  }

  @Override
  public void zIndex(final int zIndex) {
    this.zIndex = zIndex;
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

  @Override
  public Color color() {
    return color;
  }

  @Override
  public Memento save() {
    return new AbstractMemento(this);
  }

  public class AbstractMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final Color color;
    private final Shape originator;

    public AbstractMemento(final AbstractShape shape) {
      this.originator = shape;
      this.x = shape.x();
      this.y = shape.y();
      this.zIndex = shape.zIndex();
      this.width = shape.width();
      this.height = shape.height();
      this.color = shape.color();
    }

    public void restore() {
      originator.x(x);
      originator.y(y);
      originator.zIndex(zIndex);
      originator.width(width);
      originator.height(height);
      originator.color(color);
    }
  }
}
