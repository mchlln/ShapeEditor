package ubx.archilog.model;

public class Ellipsoid extends AbstractShape {

  public Ellipsoid(int x, int y, int zIndex, int width, int height, Color color) {
    super(x, y, zIndex, width, height, color);
  }

  public Ellipsoid(int x, int y, int zIndex, int width, int height) {
    super(x, y, zIndex, width, height);
  }

  @Override
  public Memento save() {
    return new EllipsoidMemento(this);
  }

  private class EllipsoidMemento implements Memento {
    int x;
    int y;
    int zIndex;
    int width;
    int height;
    Color color;
    Ellipsoid originator;

    public EllipsoidMemento(Ellipsoid s) {
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
