package ubx.archilog.view;

import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.AddToToolBarCommand;
import ubx.archilog.controller.commands.CloneToCanvasCommand;
import ubx.archilog.controller.commands.MoveCommand;
import ubx.archilog.model.*;
import ubx.archilog.model.visitor.IsInVisitor;
import ubx.archilog.model.visitor.ShapeInZoneVisitor;

public class View {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int MENU_MARGIN = 50;

  private Group menu;

  private Position from;

  private Position fromSelection = null;

  private Render renderer;

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    Model model = Model.getInstance();
    model.addComponent(createMenu());
    Group g = new Group();
    g.add(new Circle(100, 100, 1, 50, new Color(245, 0, 245, 255)));
    g.add(new Rectangle(200, 200, 1, 100, 50, new Color(0, 245, 245, 255), true));

    /*
    Circle c = new Circle(100,100,1,50,new Color(245, 0, 245, 255));
    System.out.println(c);
    model.getCanvas().add(c);

      */
    g.updateChildZIndex();
    model.getCanvas().add(g);
    updateView();
  }

  public Shape createMenu() {
    menu = new Group();
    menu.add(
        new Rectangle(
            0, 0, -1, WINDOW_WIDTH, MENU_MARGIN + 37, new Color(189, 142, 231, 50), true));
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
      clickOn(position, button);
    } else {
      mouseDragged(from, position, button);
    }
    from = null;
    updateView();
    return null;
  }

  public void clickOn(Position position, int button) {
    if (button == 3) {
      if (fromSelection == null) {
        fromSelection = position;
      } else {
        Shape shape =
            new Rectangle(
                fromSelection.x(),
                fromSelection.y(),
                0,
                Math.abs(position.x() - fromSelection.x()),
                Math.abs(position.y() - fromSelection.y()),
                new Color(255, 0, 0, 255),
                false);
        System.out.println("SELECTION: " + shape);
        // IsInVisitor visitor = new IsInVisitor()
        Model.getInstance().getCanvas().add(shape);
        ShapeInZoneVisitor zoneVisitor =
            new ShapeInZoneVisitor(
                fromSelection.x(),
                fromSelection.y(),
                Math.abs(position.x() - fromSelection.x()),
                Math.abs(position.y() - fromSelection.y()));
        Model.getInstance().getCanvas().accept(zoneVisitor);
        List<Shape> zone = zoneVisitor.getResult();
        System.out.println("ZONE: " + zone);
        fromSelection = null;
      }
    }
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
      IsInVisitor fromAppSector = new IsInVisitor(from.x(), from.y());
      IsInVisitor toAppSector = new IsInVisitor(to.x(), to.y());
      Model model = Model.getInstance();

      // Case adding shape to canvas
      model.getToolBar().accept(fromAppSector);
      model.getCanvas().accept(toAppSector);
      List<Shape> in = fromAppSector.getResult();
      List<Shape> out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new CloneToCanvasCommand(toAdd, to));
        }
      }

      // Case adding shape to toolbar
      model.getCanvas().accept(fromAppSector);
      model.getToolBar().accept(toAppSector);
      in = fromAppSector.getResult();
      out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new AddToToolBarCommand(toAdd));
        }
      }

      // Moving shape from canvas
      model.getCanvas().accept(toAppSector);
      out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        Shape toMove = model.getBestZIndex(in);
        if (toMove.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new MoveCommand(toMove, to));
        }
      }
    }
  }
}
