package ubx.archilog.model;

import ubx.archilog.model.visitor.ShapeVisitor;
import ubx.archilog.view.Render;

public class Circle extends Ellipsoid {

  public Circle(int x, int y, int zIndex, int radius, Color color) {
    super(x, y, zIndex, radius * 2, radius * 2, color);
  }

  public Circle(int x, int y, int zIndex, int radius) {
    super(x, y, zIndex, radius * 2, radius * 2);
  }

  public int getRadius() {
    return super.getWidth() / 2;
  }

  public void setRadius(int radius) {
    super.setWidth(radius * 2);
  }

  @Override
  public void rotate() {
    // no action needed for a circle
    return;
  }

  @Override
  public void draw(Render render) {
    render.drawCircle(super.getX(), super.getY(), getRadius(), super.getColor());
  }

  @Override
  public Shape clone() {
    return new Circle(super.getX(), super.getY(), super.getZindex(), getRadius(), super.getColor());
  }

  @Override
  public boolean equals(Object obj) {
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
  public void accept(ShapeVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public Memento save() {
    return new CircleMemento(this);
  }

  private class CircleMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final Color color;
    private final Circle originator;

    public CircleMemento(Circle s) {
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
