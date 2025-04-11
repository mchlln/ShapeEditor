package ubx.archilog.model;

import static ubx.archilog.view.View.*;

import java.util.List;
import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.LoadCommand;
import ubx.archilog.controller.commands.RedoCommand;
import ubx.archilog.controller.commands.SaveCommand;
import ubx.archilog.controller.commands.UndoCommand;
import ubx.archilog.model.io.FileBuilder;
import ubx.archilog.model.shapes.*;
import ubx.archilog.view.Render;

public final class Model {
  private final Group components;
  private static Model instance;

  private ToolBar toolBar;
  private Group canvas;
  private Group menu;

  private Shape currentMenu;

  private Model() {
    components = new Group();
    components.setZindex(0);
    toolBar = new ToolBar();
    menu = new Group();
    menu.setZindex(0);
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
    components.add(menu);
  }

  public void buildMenu(final Render renderer) {
    menu.add(
        new Rectangle(
            0, 0, -1, WINDOW_WIDTH, MENU_MARGIN + 37, new Color(189, 142, 231, 50), true));
    menu.add(
        new ImageRectangle(
            MENU_MARGIN,
            37,
            0,
            MENU_MARGIN,
            MENU_MARGIN,
            "/icons/import.png",
            () -> BagOfCommands.getInstance().addCommand(new LoadCommand(renderer))));
    menu.add(
        new ImageRectangle(
            10 + 2 * MENU_MARGIN,
            37,
            0,
            MENU_MARGIN,
            MENU_MARGIN,
            "/icons/export.png",
            () -> BagOfCommands.getInstance().addCommand(new SaveCommand(renderer))));
    menu.add(
        new ImageRectangle(
            10 + 3 * MENU_MARGIN,
            37,
            0,
            MENU_MARGIN,
            MENU_MARGIN,
            "/icons/undo.png",
            () -> BagOfCommands.getInstance().addCommand(new UndoCommand())));
    menu.add(
        new ImageRectangle(
            10 + 4 * MENU_MARGIN,
            37,
            0,
            MENU_MARGIN,
            MENU_MARGIN,
            "/icons/redo.png",
            () -> BagOfCommands.getInstance().addCommand(new RedoCommand())));
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

  public void addComponent(final Shape shape) {
    components.add(shape);
  }

  public void setMenu(final Group menu) {
    this.menu = menu;
  }

  public void setToolBar(final ToolBar toolBar) {
    components.remove(this.toolBar);
    this.toolBar = toolBar;
    components.add(toolBar);
  }

  public void setCanvas(final Group canvas) {
    components.remove(this.canvas);
    this.canvas = canvas;
    components.add(canvas);
  }

  public FileBuilder save(final FileBuilder fileBuilder) {
    fileBuilder.beginDocument();
    fileBuilder.beginToolBar();
    fileBuilder.endToolBar();
    fileBuilder.beginCanvas();
    fileBuilder.endCanvas();
    fileBuilder.endDocument();
    return fileBuilder;
  }

  public ToolBar getToolBar() {
    return toolBar;
  }

  public Group getCanvas() {
    return canvas;
  }

  public Group getMenu() {
    return menu;
  }

  public void setCurrentMenu(final Shape shape) {
    currentMenu = shape;
    menu.add(currentMenu);
  }

  public void clearCurrentMenu() {
    menu.remove(currentMenu);
    currentMenu = null;
  }

  /**
   * Search for the highest Z-index in the given list.
   *
   * @param shapes The list to lookup.
   * @return The shape with the highest Z-index.
   */
  public Shape getBestZIndex(final List<Shape> shapes) {
    Shape best = shapes.getFirst();
    for (final Shape shape : shapes) {
      if (shape.getZindex() > best.getZindex()) {
        best = shape;
      }
    }
    return best;
  }
}
