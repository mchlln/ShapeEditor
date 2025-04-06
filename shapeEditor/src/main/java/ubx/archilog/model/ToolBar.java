package ubx.archilog.model;

import static ubx.archilog.view.View.MENU_MARGIN;
import static ubx.archilog.view.View.WINDOW_HEIGHT;

public class ToolBar extends Group {

  private int shapeCount = 1;

  public ToolBar() {
    super();
    this.setZindex(0);
    this.add(new Rectangle(0, 0, 0, MENU_MARGIN, WINDOW_HEIGHT, new Color(189, 142, 231, 0), true));
    // this.add(new Rectangle(5, 150, 20, 30, new Color(189, 142, 231, 255)));
    this.add(
        new ImageRectangle(
            0, WINDOW_HEIGHT - (MENU_MARGIN + 10), 1, MENU_MARGIN, MENU_MARGIN, "/icons/bin.png"));
    addShapeToToolBar(new Rectangle(0, 0, 1, 50, 50, new Color(0, 25, 230, 100), true));
    addShapeToToolBar(new Rectangle(0, 0, 1, 100, 50, new Color(100, 25, 200, 100), true));
    addShapeToToolBar(new Circle(0, 0, 1, 100, new Color(230, 30, 230, 100)));
  }

  public void addShapeToToolBar(Shape s) {
    if (shapeCount >= 8) {
      return;
    }
    s.scale((float) MENU_MARGIN / Math.max(s.getWidth(), s.getHeight()));
    super.add(s);
    s.translate(-s.getX(), shapeCount * 60 + MENU_MARGIN - s.getY());
    shapeCount++;
  }

  public void removeShapeFromToolBar(Shape s) {
    super.remove(s);
    shapeCount--;
  }
}
