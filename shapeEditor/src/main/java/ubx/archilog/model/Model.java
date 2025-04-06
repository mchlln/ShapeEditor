package ubx.archilog.model;

import static ubx.archilog.view.View.*;

import ubx.archilog.model.io.FileBuilder;

public class Model {
  private Group components;
  private static Model instance;

  private ToolBar toolBar;
  private Group canvas;

  private Model() {
    components = new Group();
    toolBar = new ToolBar();
    canvas = new Group();
    canvas.add(
        new Rectangle(
            MENU_MARGIN, MENU_MARGIN + 37, 0, WINDOW_WIDTH, WINDOW_HEIGHT, new Color(0, 0, 0, 0)));
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
}
