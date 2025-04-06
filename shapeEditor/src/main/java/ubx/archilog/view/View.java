package ubx.archilog.view;

import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.model.*;
import ubx.archilog.model.visitor.IsInVisitor;

public class View {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int MENU_MARGIN = 50;

  private Group menu;

  private Position from;

  private Render renderer;

  private Shape shape = new Rectangle(200, 200, 1, 50, 50, new Color(255, 255, 1, 100));

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    Model model = Model.getInstance();
    model.addComponent(createMenu());
    model.addComponent(shape);
    model.getToolBar().addShapeToToolBar(shape);
    updateView();
  }

  public Shape createMenu() {
    menu = new Group();
    menu.add(new Rectangle(0, 0, -1, WINDOW_WIDTH, MENU_MARGIN + 37, new Color(189, 142, 231, 50)));
    menu.add(new ImageRectangle(MENU_MARGIN, 37, 0, MENU_MARGIN, MENU_MARGIN, "/icons/undo.png"));
    menu.add(
        new ImageRectangle(
            10 + 2 * MENU_MARGIN, 37, 0, MENU_MARGIN, MENU_MARGIN, "/icons/redo.png"));
    return menu;
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
      if (!(s instanceof Group)) System.out.println("Menu clicked ? " + s.toString());
    }
    System.out.println("Mouse Clicked  " + position);
  }

  public void mouseDragged(Position from, Position to, int b) {
    if (b == 1) {
      IsInVisitor appSector = new IsInVisitor(from.x(), from.y());
      Model model = Model.getInstance();
      model.getToolBar().accept(appSector);
      List<Shape> in = appSector.getResult();
      if (!in.isEmpty()) {
        System.out.println(in);
        Shape toAdd = in.get(2).clone();
        toAdd.translate(to.x(), to.y());
        System.out.println(toAdd.toString());
        model.getComponents().add(toAdd);
      }
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
      Model.getInstance().getToolBar().addShapeToToolBar(s1);
    } else if (b == 3) {
      Model.getInstance()
          .addComponent(
              new Circle(from.x(), from.y(), 1, to.y() - from.y(), new Color(189, 142, 231, 255)));
    }
    System.out.println("Mouse Dragged  " + from + "," + to);
  }
}
