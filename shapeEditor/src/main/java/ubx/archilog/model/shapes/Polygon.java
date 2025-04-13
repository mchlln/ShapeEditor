package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;

public class Polygon extends AbstractShape {
  private int sides;

  public Polygon(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final int sides,
      final Color color) {
    super(x, y, zIndex, width, height, color);
    this.sides = sides;
  }

  public Polygon(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final int sides) {
    super(x, y, zIndex, width, height);
    this.sides = sides;
  }

  public int sides() {
    return this.sides;
  }

  public void sides(final int sides) {
    this.sides = sides;
  }
}
