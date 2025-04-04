package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Circle extends Ellipsoid {
  private int radius;

  public Circle(int x, int y, int radius, Color color) {
    super(x, y, color);
    this.radius = radius;
  }

  public Circle(int x, int y, int radius) {
    super(x, y);
    this.radius = radius;
  }

  public int getRadius() {
    return radius;
  }

  @Override
  public void rotate() {
    // no action needed for a square
    return;
  }

  @Override
  public void draw(Render render) {
    render.drawCircle(super.getX(), super.getY(), radius, super.getColor());
  }

  @Override
  public Shape clone() {
    return new Circle(super.getX(), super.getY(), radius, super.getColor());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Circle) {
      return this.radius == ((Circle) obj).radius && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + radius;
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + super.getX()
        + ", y="
        + super.getY()
        + ", radius= "
        + radius
        + ", color="
        + super.getColor()
        + "]";
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }
}
