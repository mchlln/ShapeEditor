package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Square extends Polygon {
  private int width;

  public Square(int x, int y, int width) {
    super(x, y, 4);
    this.width = width;
  }

  public Square(int x, int y, int width, Color color) {
    super(x, y, 4, color);
    this.width = width;
  }

  public int getWidth() {
    return width;
  }

  @Override
  public void rotate() {
    // no action needed for a square
    return;
  }

  @Override
  public void draw(Render render) {
    render.drawRect(super.getX(), super.getY(), width, width, true, super.getColor());
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    return new Square(super.getX(), super.getY(), width, super.getColor());
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Square) {
      return this.width == ((Square) obj).width && super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + width;
    return result;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + " [x="
        + super.getX()
        + ", y="
        + super.getY()
        + ", size= "
        + width
        + ", color="
        + super.getColor()
        + "]";
  }
}
