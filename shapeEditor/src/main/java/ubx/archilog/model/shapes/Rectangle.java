package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
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
    final int tmp = super.width();
    super.width(super.height());
    super.height(tmp);
  }

  @Override
  public void draw(final Render render) {
    render.drawRect(super.x(), super.y(), super.width(), super.height(), fill, super.color());
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    return new Rectangle(
        super.x(), super.y(), super.zIndex(), super.width(), super.height(), super.color(), fill);
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Rectangle) {
      return height() == ((Rectangle) obj).height()
          && width() == ((Rectangle) obj).width()
          && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + width();
    result = 31 * result + height();
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + x()
        + ", y="
        + y()
        + ", zIndex="
        + super.zIndex()
        + ", width= "
        + width()
        + ", height="
        + height()
        + ", color="
        + color()
        + "]";
  }
}
