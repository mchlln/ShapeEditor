package ubx.archilog.model.shapes;

import ubx.archilog.model.Color;
import ubx.archilog.view.Render;

public class RegularPolygon extends Polygon {
  public RegularPolygon(int x, int y, int zIndex, int width, int height, int sides, Color color) {
    super(x, y, zIndex, width, height, sides, color);
  }

  @Override
  public void draw(final Render render) {
    final int sides = getSides();
    if (sides < 3) return;
    double angle = 2 * Math.PI / sides;
    double radius = getWidth() / 2.0;
    double rotationRadians = Math.toRadians(0);

    int centerX = getX() + getWidth() / 2;
    int centerY = getY() + getWidth() / 2;

    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];

    for (int i = 0; i < sides; i++) {
      double theta = i * angle - Math.PI / 2 + rotationRadians;
      xPoints[i] = (int) (centerX + radius * Math.cos(theta));
      yPoints[i] = (int) (centerY + radius * Math.sin(theta));
    }
    render.drawPolygon(xPoints, yPoints, getSides(), super.getColor());
  }

  @Override
  public Shape clone() {
    return new RegularPolygon(
        getX(), getY(), getZindex(), getWidth(), getHeight(), getSides(), getColor());
  }
}
