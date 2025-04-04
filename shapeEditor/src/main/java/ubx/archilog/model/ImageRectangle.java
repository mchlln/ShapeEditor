package ubx.archilog.model;

import ubx.archilog.view.Render;

public class ImageRectangle extends Rectangle {
  private String path;

  public ImageRectangle(int x, int y, int width, int height, String path) {
    super(x, y, width, height);
    this.path = path;
  }

  @Override
  public void draw(Render render) {
    render.drawImageRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), path);
  }
}
