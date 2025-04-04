package ubx.archilog.model;

public class Rectangle extends Polygon {
  private int width;
  private int height;

  public Rectangle(int x, int y, int width, int height) {
    super(x, y, 4);
    this.width = width;
    this.height = height;
  }

  public Rectangle(int x, int y, int width, int height, Color color) {
    super(x, y, 4, color);
    this.width = width;
    this.height = height;
  }

  @Override
  public void rotate() {
    int tmp = this.width;
    this.width = this.height;
    this.height = tmp;
  }
}
