package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Circle extends Ellipsoid {

  public Circle(final int x, final int y, final int zIndex, final int radius, final Color color) {
    super(x, y, zIndex, radius * 2, radius * 2, color);
  }

  public Circle(final int x, final int y, final int zIndex, final int radius) {
    super(x, y, zIndex, radius * 2, radius * 2);
  }

  public int radius() {
    return super.width() / 2;
  }

  public void radius(final int radius) {
    super.width(radius * 2);
  }

  @Override
  public void rotate() {
    // no action needed for a circle
    return;
  }

  @Override
  public void draw(final Render render) {
    render.drawCircle(super.x(), super.y(), radius(), super.color());
  }

  @Override
  public Shape clone() {
    return new Circle(super.x(), super.y(), super.zIndex(), radius(), super.color());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Circle) {
      return this.radius() == ((Circle) obj).radius() && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + radius();
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + super.x()
        + ", y="
        + super.y()
        + ", zIndex="
        + super.zIndex()
        + ", radius= "
        + radius()
        + ", color="
        + super.color()
        + "]";
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
