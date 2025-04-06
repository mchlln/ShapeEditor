package ubx.archilog.view;

import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.model.*;
import ubx.archilog.model.visitor.IsInVisitor;

public class View {
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;
  private static final int MENU_MARGIN = 50;

  private Group menu;
  private Group toolbar;

  private Position from;

  private Render renderer;

  private Shape shape = new Rectangle(200, 200, 50, 50, new Color(255, 255, 1, 100));

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    Model model = Model.getInstance();
    model.addComponent(createMenu());
    model.addComponent(createToolbar());
    model.addComponent(shape);
    updateView();
  }

  public Shape createMenu() {
    menu = new Group();
    menu.add(new Rectangle(0, 0, WINDOW_WIDTH, MENU_MARGIN + 37, new Color(189, 142, 231, 50)));
    menu.add(new ImageRectangle(MENU_MARGIN, 37, MENU_MARGIN, MENU_MARGIN, "/icons/undo.png"));
    menu.add(
        new ImageRectangle(10 + 2 * MENU_MARGIN, 37, MENU_MARGIN, MENU_MARGIN, "/icons/redo.png"));
    return menu;
  }

  public Shape createToolbar() {
    toolbar = new Group();
    toolbar.add(new Rectangle(0, 0, MENU_MARGIN, WINDOW_HEIGHT, new Color(189, 142, 231, 50)));
    toolbar.add(new Rectangle(5, 150, 20, 30, new Color(189, 142, 231, 255)));
    toolbar.add(
        new ImageRectangle(
            0, WINDOW_HEIGHT - (MENU_MARGIN + 10), MENU_MARGIN, MENU_MARGIN, "/icons/bin.png"));
    return toolbar;
  }

  public void createCanva() {}

  public void updateView() {
    for (Shape s : Model.getInstance().getComponents().getShapes()) {
      s.draw(renderer);
    }
    renderer.update();
  }

  public Void mousePressed(Position position, int button) {
    from = position;
    return null;
  }

  public Void mouseReleased(Position position, int button) {
    if (from.equals(position)) {
      clickOn(position);
    } else {
      mouseDragged(from, position, button);
    }
    from = null;
    updateView();
    return null;
  }

  public void clickOn(Position position) {
    // renderer.drawCircle(position.x(), position.y(), 100, new Color(189, 142, 231, 255));
    IsInVisitor visitor = new IsInVisitor(position.x(), position.y());
    // menu.accept(visitor);
    Model.getInstance().getComponents().accept(visitor);
    for (Shape s : visitor.getResult()) {
      System.out.println("Menu clicked ? " + s.toString());
    }

    System.out.println("Mouse Clicked  " + position);
  }

  public void mouseDragged(Position from, Position to, int b) {
    if (b == 1) {
      IsInVisitor menuVisitor = new IsInVisitor(from.x(), from.y());
      menu.accept(menuVisitor);
      if (!menuVisitor.getResult().isEmpty()) {}
      /*Model.getInstance()
      .addComponent(
          new Rectangle(
              from.x(),
              from.y(),
              to.x() - from.x(),
              to.y() - from.y(),
              new Color(189, 142, 231, 255)));*/
      IsInVisitor visitor = new IsInVisitor(from.x(), from.y());
      // Model.getInstance().getComponents().accept(visitor);
      shape.accept(visitor);
      List<Shape> g1 = visitor.getResult();
      if (g1.isEmpty() || to.x() > MENU_MARGIN) {
        return;
      }
      Shape s1 = shape.clone();
      s1.translate(-s1.getX(), to.y() - s1.getY());
      System.out.println("at: " + s1.getX() + ", " + s1.getY());
      toolbar.add(s1);
    } else if (b == 3) {
      Model.getInstance()
          .addComponent(
              new Circle(from.x(), from.y(), to.y() - from.y(), new Color(189, 142, 231, 255)));
    }
    System.out.println("Mouse Dragged  " + from + "," + to);
  }
}
