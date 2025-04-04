package ubx.archilog.model;

import ubx.archilog.view.Render;

public class Circle extends Ellipsoid {
  private int radius;

  public Circle(int x, int y, int radius, Color color) {
    super(x, y, color);
    this.radius = radius;
  }

  public Circle(int x, int y, int radius) {
    super(x, y);
    this.radius = radius;
  }

  @Override
  public void rotate() {
    // no action needed for a square
    return;
  }

  @Override
  public void draw(Render render) {
    // render.drawCircle()
  }
}
