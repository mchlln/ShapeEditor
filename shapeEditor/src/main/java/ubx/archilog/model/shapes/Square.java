package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Square extends Polygon {

  public Square(final int x, final int y, final int zIndex, final int width) {
    super(x, y, zIndex, width, width, 4);
  }

  public Square(final int x, final int y, final int zIndex, final int width, final Color color) {
    super(x, y, zIndex, width, width, 4, color);
  }

  @Override
  public void rotate() {
    // no action needed for a square
  }

  @Override
  public void draw(final Render render) {
    render.drawRect(
        super.getX(), super.getY(), super.getWidth(), super.getHeight(), true, super.getColor());
  }

  @Override
  public void accept(final ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    return new Square(
        super.getX(), super.getY(), super.getZindex(), super.getWidth(), super.getColor());
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof Square) {
      return getWidth() == ((Square) obj).getWidth() && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getWidth();
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
        + ", size= "
        + getWidth()
        + ", color="
        + super.getColor()
        + "]";
  }
}
