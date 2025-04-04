package ubx.archilog.view;

import java.util.function.BiFunction;
import ubx.archilog.model.*;
import ubx.archilog.model.visitor.IsInVisitor;

public class View {
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;
  private static final int MENU_MARGIN = 32;

  private Group menu;
  private Group toolbar;

  private Position from;

  private Render renderer;

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    Model model = Model.getInstance();
    model.addComponent(createMenu());
    model.addComponent(createToolbar());
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
    menu.accept(visitor);
    System.out.println("Menu clicked ? " + visitor.getResult());
    System.out.println("Mouse Clicked  " + position);
  }

  public void mouseDragged(Position from, Position to, int b) {
    if (b == 1) {
      Model.getInstance()
          .addComponent(
              new Rectangle(
                  from.x(),
                  from.y(),
                  to.x() - from.x(),
                  to.y() - from.y(),
                  new Color(189, 142, 231, 255)));
    } else if (b == 3) {
      Model.getInstance()
          .addComponent(
              new Circle(from.x(), from.y(), to.y() - from.y(), new Color(189, 142, 231, 255)));
    }
    System.out.println("Mouse Dragged  " + from + "," + to);
  }
}
