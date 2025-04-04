package ubx.archilog.model;

public class Ellipsoid extends AbstractShape {
  private int x;
  private int y;
  private Color color = new Color(245, 40, 145, 255);

  public Ellipsoid(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public Ellipsoid(int x, int y) {
    this.x = x;
    this.y = y;
  }
}
