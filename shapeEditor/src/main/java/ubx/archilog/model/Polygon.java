package ubx.archilog.model;

public class Polygon extends AbstractShape {
  private final int sides;

  public Polygon(int x, int y, int sides, Color color) {
    super(x, y, color);
    this.sides = sides;
  }

  public Polygon(int x, int y, int sides) {
    super(x, y);
    this.sides = sides;
  }
}
