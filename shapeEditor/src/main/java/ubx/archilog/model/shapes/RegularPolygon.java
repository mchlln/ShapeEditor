package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.model.Memento;
import ubx.archilog.view.Render;

public class RegularPolygon extends Polygon {
  private int rotation;

  public RegularPolygon(int x, int y, int zIndex, int width, int height, int sides, Color color) {
    super(x, y, zIndex, width, height, sides, color);
  }

  private RegularPolygon(
      int x, int y, int zIndex, int width, int height, int sides, Color color, int rotation) {
    super(x, y, zIndex, width, height, sides, color);
    this.rotation = rotation;
  }

  @Override
  public void draw(final Render render) {
    final int sides = sides();
    if (sides < 3) return;
    double angle = 2 * Math.PI / sides;
    double radius = width() / 2.0;
    double rotationRadians =
        Math.toRadians(rotation); // Keep magic number for future rotation implementation

    int centerX = x() + width() / 2;
    int centerY = y() + width() / 2;

    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];

    for (int i = 0; i < sides; i++) {
      double theta = i * angle - Math.PI / 2 + rotationRadians;
      xPoints[i] = (int) (centerX + radius * Math.cos(theta));
      yPoints[i] = (int) (centerY + radius * Math.sin(theta));
    }
    render.drawPolygon(xPoints, yPoints, sides(), super.color());
  }

  @Override
  public Shape clone() {
    return new RegularPolygon(x(), y(), zIndex(), width(), height(), sides(), color(), rotation);
  }

  public int rotation() {
    return this.rotation;
  }

  public void rotation(int rotation) {
    this.rotation = rotation;
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
        + ", sides="
        + sides()
        + ", width= "
        + width()
        + ", height="
        + height()
        + ", rotation="
        + rotation
        + ", color="
        + color()
        + "]";
  }

  @Override
  public Memento save() {
    return new RegularPolygonMemento(this);
  }

  @Override
  public void rotate() {
    rotation += 10;
  }

  private class RegularPolygonMemento extends AbstractMemento {
    private final int sides;
    private final Shape shape;
    private final int rotation;

    public RegularPolygonMemento(final AbstractShape shape) {
      super(shape);
      this.shape = shape;
      if (shape instanceof RegularPolygon regularPolygon) {
        this.sides = regularPolygon.sides();
        this.rotation = regularPolygon.rotation;
      } else {
        this.sides = -1;
        this.rotation = -1;
      }
    }

    public void restore() {
      super.restore();
      if (shape instanceof RegularPolygon regularPolygon) {
        regularPolygon.sides(sides);
        regularPolygon.rotation = rotation;
      }
    }
  }
}
