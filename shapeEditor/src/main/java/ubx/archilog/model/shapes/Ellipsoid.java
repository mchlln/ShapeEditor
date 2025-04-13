package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;

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
}
