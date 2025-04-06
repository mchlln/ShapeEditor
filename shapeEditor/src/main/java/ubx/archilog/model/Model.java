package ubx.archilog.model;

import static ubx.archilog.view.View.*;

import java.util.List;
import ubx.archilog.model.io.FileBuilder;

public class Model {
  private Group components;
  private static Model instance;

  private ToolBar toolBar;
  private Group canvas;

  private Model() {
    components = new Group();
    components.setZindex(0);
    toolBar = new ToolBar();
    canvas = new Group();
    canvas.setZindex(0);
    canvas.add(
        new Rectangle(
            MENU_MARGIN,
            MENU_MARGIN + 37,
            0,
            WINDOW_WIDTH,
            WINDOW_HEIGHT,
            new Color(0, 0, 0, 0),
            true));
    components.add(toolBar);
    components.add(canvas);
  }

  public static Model getInstance() {
    if (instance == null) {
      instance = new Model();
    }
    return instance;
  }

  public Group getComponents() {
    return components;
  }

  public void addComponent(Shape s) {
    components.add(s);
  }

  public FileBuilder save(FileBuilder fb) {
    fb.beginDocument();
    fb.beginToolBar();
    fb.beginGroup();
    fb.endGroup();
    fb.endToolBar();
    fb.endDocument();
    return fb;
  }

  public ToolBar getToolBar() {
    return toolBar;
  }

  public Group getCanvas() {
    return canvas;
  }

  /**
   * Search for the highest Z-index in the given list.
   *
   * @param shapes The list to lookup.
   * @return The shape with the highest Z-index.
   */
  public Shape getBestZIndex(List<Shape> shapes) {
    Shape best = shapes.getFirst();
    for (Shape s : shapes) {
      if (s.getZindex() > best.getZindex()) {
        best = s;
      }
    }
    return best;
  }
}
