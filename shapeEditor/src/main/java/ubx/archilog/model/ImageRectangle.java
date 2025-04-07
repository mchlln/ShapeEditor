package ubx.archilog.model;

import ubx.archilog.model.buttonActions.ButtonAction;
import ubx.archilog.view.Render;

public class ImageRectangle extends Rectangle {
  private final String path;
  private final ButtonAction action;

  public ImageRectangle(
      int x, int y, int zIndex, int width, int height, String path, ButtonAction action) {
    super(x, y, zIndex, width, height, false);
    this.path = path;
    this.action = action;
  }

  @Override
  public void draw(Render render) {
    render.drawImageRect(super.getX(), super.getY(), super.getWidth(), super.getHeight(), path);
  }

  public ButtonAction getAction() {
    return action;
  }
}
