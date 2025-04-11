package ubx.archilog.model;

public class Polygon extends AbstractShape {
  private int sides;

  public Polygon(int x, int y, int zIndex, int width, int height, int sides, Color color) {
    super(x, y, zIndex, width, height, color);
    this.sides = sides;
  }

  public Polygon(int x, int y, int zIndex, int width, int height, int sides) {
    super(x, y, zIndex, width, height);
    this.sides = sides;
  }

  public int getSides() {
    return this.sides;
  }

  public void setSides(int sides) {
    this.sides = sides;
  }

  @Override
  public Memento save() {
    return new PolygonMemento(this);
  }

  private class PolygonMemento implements Memento {
    int x;
    int y;
    int zIndex;
    int width;
    int height;
    int sides;
    Color color;
    Polygon originator;

    public PolygonMemento(Polygon s) {
      this.originator = s;
      this.x = s.getX();
      this.y = s.getY();
      this.zIndex = s.getZindex();
      this.width = s.getWidth();
      this.height = s.getHeight();
      this.sides = s.getSides();
      this.color = s.getColor(); // TODO : add a clone to color
    }

    public void restore() {
      originator.setX(x);
      originator.setY(y);
      originator.setZindex(zIndex);
      originator.setWidth(width);
      originator.setHeight(height);
      originator.setSides(sides);
      originator.setColor(color);
    }
  }
}
