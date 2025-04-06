package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Rectangle extends Polygon {

  public Rectangle(int x, int y, int zIndex, int width, int height) {
    super(x, y, zIndex, width, height, 4);
  }

  public Rectangle(int x, int y, int zIndex, int width, int height, Color color) {
    super(x, y, zIndex, width, height, 4, color);
  }

  @Override
  public void rotate() {
    int tmp = super.getWidth();
    super.setWidth(super.getHeight());
    super.setHeight(tmp);
  }

  @Override
  public void draw(Render render) {
    render.drawRect(
        super.getX(), super.getY(), super.getWidth(), super.getHeight(), true, super.getColor());
  }

  @Override
  public void accept(ShapeVisitor visitor) {
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
        super.getColor());
  }

  @Override
  public boolean equals(Object obj) {
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
}
