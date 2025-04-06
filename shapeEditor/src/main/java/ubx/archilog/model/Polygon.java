package ubx.archilog.model;

public class Polygon extends AbstractShape {
  private final int sides;

  public Polygon(int x, int y, int zIndex, int width, int height, int sides, Color color) {
    super(x, y, zIndex, width, height, color);
    this.sides = sides;
  }

  public Polygon(int x, int y, int zIndex, int width, int height, int sides) {
    super(x, y, zIndex, width, height);
    this.sides = sides;
  }
}
