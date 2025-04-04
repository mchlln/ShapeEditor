package ubx.archilog.model;

public class Square extends Polygon {
  private int width;

  public Square(int x, int y, int width) {
    super(x, y, 4);
    this.width = width;
  }

  public Square(int x, int y, int width, Color color) {
    super(x, y, 4, color);
    this.width = width;
  }

  @Override
  public void rotate() {
    // no action needed for a square
    return;
  }
}
