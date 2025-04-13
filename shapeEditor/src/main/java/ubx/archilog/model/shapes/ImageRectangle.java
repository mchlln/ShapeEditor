package ubx.archilog.model.shapes;

import ubx.archilog.view.Render;

public class ImageRectangle extends Rectangle {
  private final String path;
  private final Runnable action;

  public ImageRectangle(
      final int x,
      final int y,
      final int zIndex,
      final int width,
      final int height,
      final String path,
      final Runnable action) {
    super(x, y, zIndex, width, height, false);
    this.path = path;
    this.action = action;
  }

  @Override
  public void draw(final Render render) {
    render.drawImageRect(super.x(), super.y(), super.width(), super.height(), path);
  }

  public Runnable action() {
    return action;
  }
}
