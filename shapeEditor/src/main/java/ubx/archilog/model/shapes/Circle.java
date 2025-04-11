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

  public int getRadius() {
    return super.getWidth() / 2;
  }

  public void setRadius(final int radius) {
    super.setWidth(radius * 2);
  }

  @Override
  public void rotate() {
    // no action needed for a circle
    return;
  }

  @Override
  public void draw(final Render render) {
    render.drawCircle(super.getX(), super.getY(), getRadius(), super.getColor());
  }

  @Override
  public Shape clone() {
    return new Circle(super.getX(), super.getY(), super.getZindex(), getRadius(), super.getColor());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Circle) {
      return this.getRadius() == ((Circle) obj).getRadius() && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getRadius();
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + super.getX()
        + ", y="
        + super.getY()
        + ", zIndex="
        + super.getZindex()
        + ", radius= "
        + getRadius()
        + ", color="
        + super.getColor()
        + "]";
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
