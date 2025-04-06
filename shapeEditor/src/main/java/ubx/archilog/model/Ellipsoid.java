package ubx.archilog.model;

public class Ellipsoid extends AbstractShape {

  public Ellipsoid(int x, int y, int zIndex, int width, int height, Color color) {
    super(x, y, zIndex, width, height, color);
  }

  public Ellipsoid(int x, int y, int zIndex, int width, int height) {
    super(x, y, zIndex, width, height);
  }
}
