package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public abstract class AbstractShape implements Shape {
  private int x;
  private int y;
  private Color color = new Color(245, 40, 145, 255);

  public AbstractShape(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public AbstractShape(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public void draw(Render render) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void rotate() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void scale() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void translate(int xDiff, int yDiff) {
    x += xDiff;
    y += yDiff;
  }

  @Override
  public int getX() {
    return x;
  }

  @Override
  public int getY() {
    return y;
  }

  public Color getColor() {
    return color;
  }

  @Override
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Shape clone() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(Object obj) {
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
    return this.getClass() + " [x=" + x + ", y=" + y + ", color=" + color + "]";
  }
}
