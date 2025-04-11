package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Rectangle extends Polygon {
  boolean fill;

  public Rectangle(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final boolean fill) {
    super(x, y, zIndex, width, height, 4);
    this.fill = fill;
  }

  public Rectangle(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final Color color,
      final boolean fill) {
    super(x, y, zIndex, width, height, 4, color);
    this.fill = fill;
  }

  public boolean isFill() {
    return this.fill;
  }

  public void setFill(final boolean fill) {
    this.fill = fill;
  }

  @Override
  public void rotate() {
    final int tmp = super.getWidth();
    super.setWidth(super.getHeight());
    super.setHeight(tmp);
  }

  @Override
  public void draw(final Render render) {
    render.drawRect(
        super.getX(), super.getY(), super.getWidth(), super.getHeight(), fill, super.getColor());
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    return new Rectangle(
        super.getX(),
        super.getY(),
        super.getZindex(),
        super.getWidth(),
        super.getHeight(),
        super.getColor(),
        fill);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Rectangle) {
      return getHeight() == ((Rectangle) obj).getHeight()
          && getWidth() == ((Rectangle) obj).getWidth()
          && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getWidth();
    result = 31 * result + getHeight();
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + getX()
        + ", y="
        + getY()
        + ", zIndex="
        + super.getZindex()
        + ", width= "
        + getWidth()
        + ", height="
        + getHeight()
        + ", color="
        + getColor()
        + "]";
  }

  @Override
  public Memento save() {
    return new RectangleMemento(this);
  }

  private class RectangleMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final Color color;
    private final Rectangle originator;

    public RectangleMemento(final Rectangle s) {
      this.originator = s;
      this.x = s.getX();
      this.y = s.getY();
      this.zIndex = s.getZindex();
      this.width = s.getWidth();
      this.height = s.getHeight();
      this.color = s.getColor(); // TODO : add a clone to color
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
