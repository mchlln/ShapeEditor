package ubx.archilog.view;

import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.AddToToolBarCommand;
import ubx.archilog.controller.commands.CloneToCanvasCommand;
import ubx.archilog.controller.commands.EditShapeCommand;
import ubx.archilog.controller.commands.MoveCommand;
import ubx.archilog.model.*;
import ubx.archilog.model.visitor.IsInVisitor;
import ubx.archilog.model.visitor.ShapeInZoneVisitor;

public class View {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int MENU_MARGIN = 50;

  private Position from;

  private Position fromSelection = null;

  private Render renderer;

  private Shape selection;

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    Model model = Model.getInstance();
    Group g = new Group();
    g.add(new Circle(100, 100, 1, 50, new Color(245, 0, 245, 255)));
    g.add(new Rectangle(200, 200, 1, 100, 50, new Color(0, 245, 245, 255), true));
    g.updateChildZIndex();
    model.getCanvas().add(g);
    updateView();
  }

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
    // Model.getInstance().clearCurrentMenu();
    Model.getInstance().getCanvas().remove(selection);
    if (button == 1) {
      IsInVisitor visitor = new IsInVisitor(position.x(), position.y());
      Model.getInstance().getMenu().accept(visitor);
      List<Shape> in = visitor.getResult();
      if (!in.isEmpty()) {
        for (Shape s : in) {
          if (s instanceof ImageRectangle b) {
            b.getAction().run();
          }
        }
      }
    } else if (button == 3) {
      IsInVisitor visitor = new IsInVisitor(position.x(), position.y());
      Model.getInstance().getCanvas().accept(visitor);
      List<Shape> in = visitor.getResult();
      if (!in.isEmpty()) {
        System.out.println("CLICKED ON = " + in);
        Shape best = Model.getInstance().getBestZIndex(in);
        System.out.println("best = " + best);
        if (best.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new EditShapeCommand(best, renderer));
        }
      }

      if (fromSelection == null) {
        fromSelection = position;
      } else {
        fromSelection = null;
      }
    }
  }

  public void mouseDragged(Position from, Position to, int b) {
    Model.getInstance().getCanvas().remove(selection);
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
    } else if (b == 3) {
      selection =
          new Rectangle(
              from.x(),
              from.y(),
              0,
              Math.abs(to.x() - from.x()),
              Math.abs(to.y() - from.y()),
              new Color(255, 0, 0, 255),
              false);
      System.out.println("SELECTION: " + selection);
      // IsInVisitor visitor = new IsInVisitor()
      Model.getInstance().getCanvas().add(selection);
      ShapeInZoneVisitor zoneVisitor =
          new ShapeInZoneVisitor(
              from.x(), from.y(), Math.abs(to.x() - from.x()), Math.abs(to.y() - from.y()));
      Model.getInstance().getCanvas().accept(zoneVisitor);
      List<Shape> zone = zoneVisitor.getResult();
      if (!zone.isEmpty()) {
        Group newGroup = new Group();
        for (Shape s : zone) {
          newGroup.add(s);
        }
        newGroup.updateChildZIndex();
        Model.getInstance().getCanvas().add(newGroup);
      }
    }
    Model.getInstance().clearCurrentMenu();
  }
}
