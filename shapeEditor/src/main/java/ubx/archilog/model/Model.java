package ubx.archilog.model;

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
    components.add(toolBar);
    canvas.add(canvas);
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
