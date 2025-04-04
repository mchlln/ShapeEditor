package ubx.archilog.model;

public class Polygon extends AbstractShape {
  private int x;
  private int y;
  private int sides;
  private Color color = new Color(245, 40, 145, 255);

  public Polygon(int x, int y, int sides, Color color) {
    this.x = x;
    this.y = y;
    this.sides = sides;
    this.color = color;
  }

  public Polygon(int x, int y, int sides) {
    this.x = x;
    this.y = y;
    this.sides = sides;
  }
}
