package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Rectangle extends Polygon {
  private int width;
  private int height;

  public Rectangle(int x, int y, int width, int height) {
    super(x, y, 4);
    this.width = width;
    this.height = height;
  }

  public Rectangle(int x, int y, int width, int height, Color color) {
    super(x, y, 4, color);
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  @Override
  public void rotate() {
    int tmp = this.width;
    this.width = this.height;
    this.height = tmp;
  }

  @Override
  public void draw(Render render) {
    render.drawRect(super.getX(), super.getY(), width, height, true, super.getColor());
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    return new Rectangle(super.getX(), super.getY(), width, height, super.getColor());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Rectangle) {
      return this.height == ((Rectangle) obj).height
          && this.width == ((Rectangle) obj).width
          && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + width;
    result = 31 * result + height;
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + super.getX()
        + ", y="
        + super.getY()
        + ", width= "
        + width
        + ", height="
        + height
        + ", color="
        + super.getColor()
        + "]";
  }
}
