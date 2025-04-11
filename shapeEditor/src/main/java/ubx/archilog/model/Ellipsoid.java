package ubx.archilog.model;

public class Ellipsoid extends AbstractShape {

  public Ellipsoid(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final Color color) {
    super(x, y, zIndex, width, height, color);
  }

  public Ellipsoid(final int x, final int y, final int zIndex, final int width, final int height) {
    super(x, y, zIndex, width, height);
  }

  @Override
  public Memento save() {
    return new EllipsoidMemento(this);
  }

  private class EllipsoidMemento implements Memento {
    private final int x;
    private final int y;
    private final int zIndex;
    private final int width;
    private final int height;
    private final Color color;
    private final Ellipsoid originator;

    public EllipsoidMemento(final Ellipsoid s) {
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
