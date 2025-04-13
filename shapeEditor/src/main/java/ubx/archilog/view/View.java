package ubx.archilog.view;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.*;
import ubx.archilog.model.*;
import ubx.archilog.model.shapes.*;
import ubx.archilog.model.visitor.IsInVisitor;
import ubx.archilog.model.visitor.ShapeInZoneVisitor;

public class View {
  private static final int MOUSE_TOLERANCE = 5;
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int MENU_MARGIN = 50;
  public static final int TOP_PADDING = 37;
  public static final int DEFAULT_Z_INDEX = 1;
  public static final int DEFAULT_ICON_SIZE = 50;

  private Position from;

  private Render renderer;

  private Shape selection;

  public View() {
    renderer = new AwtRenderer();
    final BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    final BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    final Function<Void, Void> quit = this::quitCallback;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased, quit);
    final Model model = Model.getInstance();
    model.buildMenu(renderer);
    final Group group = new Group();
    group.add(new Circle(100, 100, 1, 50, new Color(245, 0, 245, 255)));
    group.add(new Rectangle(200, 200, 1, 100, 50, new Color(0, 245, 245, 255), true));
    group.updateChildZIndex();
    model.getCanvas().add(group);
    updateView();
  }

  public Void quitCallback(Void unused) {
    return null;
  }

  public void updateView() {
    for (final Shape shape : Model.getInstance().getComponents().getShapes()) {
      shape.draw(renderer);
    }
    renderer.update();
  }

  public Void mousePressed(final Position position, final int button) {
    from = position;
    return null;
  }

  private boolean isWithinTolerance(Position a, Position b) {
    int dx = a.x() - b.x();
    int dy = a.y() - b.y();
    return dx * dx + dy * dy <= MOUSE_TOLERANCE * MOUSE_TOLERANCE;
  }

  public Void mouseReleased(final Position position, final int button) {
    if (from != null && isWithinTolerance(from, position)) {
      clickOn(position, button);
    } else {
      mouseDragged(from, position, button);
    }
    from = null;
    updateView();
    return null;
  }

  public void clickOn(final Position position, final int button) {
    // Model.getInstance().clearCurrentMenu();
    Model.getInstance().getCanvas().remove(selection);
    if (button == 1) {
      IsInVisitor visitor = new IsInVisitor(position.x(), position.y());
      Model.getInstance().getMenu().accept(visitor);
      List<Shape> in = visitor.getResult();
      if (!in.isEmpty()) {
        for (final Shape shape : in) {
          if (shape instanceof ImageRectangle imageRectangle) {
            imageRectangle.getAction().run();
          } else if (shape instanceof Group group) {
            visitor = new IsInVisitor(position.x(), position.y());
            group.accept(visitor);
            in = visitor.getResult();
            System.out.println("IN: " + in);
            final Shape clickedButton = Model.getInstance().getBestZIndex(in);
            if (clickedButton instanceof ImageRectangle bu) {
              bu.getAction().run();
            }
          }
        }
      }
    } else if (button == 3) {
      final IsInVisitor visitor = new IsInVisitor(position.x(), position.y());
      Model.getInstance().getCanvas().accept(visitor);
      final List<Shape> in = visitor.getResult();
      if (!in.isEmpty()) {
        final Shape best = Model.getInstance().getBestZIndex(in);
        if (best.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new EditShapeCommand(best, renderer));
        }
      }
    }
  }

  private Shape detect(
      final Shape in, final Shape out, final Position from, final Position to, boolean clone) {
    final IsInVisitor fromAppSector = new IsInVisitor(from.x(), from.y());
    final IsInVisitor toAppSector = new IsInVisitor(to.x(), to.y());

    in.accept(fromAppSector);
    out.accept(toAppSector);
    List<Shape> inShapes = fromAppSector.getResult();
    List<Shape> outShapes = toAppSector.getResult();
    if (!inShapes.isEmpty() && !outShapes.isEmpty()) {
      if (clone) {
        return Model.getInstance().getBestZIndex(inShapes).clone();
      } else {
        return Model.getInstance().getBestZIndex(inShapes);
      }
    }
    return null;
  }

  public void mouseDragged(final Position from, final Position to, final int b) {
    Model.getInstance().getCanvas().remove(selection);
    if (b == 1) {
      final Model model = Model.getInstance();

      // Case adding shape to canvas
      Shape s = detect(model.getToolBar(), model.getCanvas(), from, to, true);
      if (s != null && s.getZindex() > 0) {
        BagOfCommands.getInstance().addCommand(new CloneToCanvasCommand(s, to));
      }

      // Case adding shape to toolbar
      // Special case for delete from canvas
      s = detect(model.getCanvas(), model.getToolBar().getBin(), from, to, true);
      if (s != null && s.getZindex() > 0) {
        BagOfCommands.getInstance()
            .addCommand(new DeleteCommand(s, Model.getInstance().getCanvas()));
      }

      s = detect(model.getCanvas(), model.getToolBar(), from, to, true);
      if (s != null && s.getZindex() > 0) {
        BagOfCommands.getInstance().addCommand(new AddToToolBarCommand(s));
      }

      // Moving shape from canvas
      s = detect(model.getCanvas(), model.getCanvas(), from, to, false);
      if (s != null && s.getZindex() > 0) {
        final int deltaX = to.x() - from.x();
        final int deltaY = to.y() - from.y();
        BagOfCommands.getInstance().addCommand(new TranslateCommand(s, deltaX, deltaY));
      }

      // Special case to forbid bin deletion
      s = detect(model.getToolBar().getBin(), model.getToolBar().getBin(), from, to, true);
      if (s != null && s.getZindex() > 0) {
        return;
      }

      s = detect(model.getToolBar(), model.getToolBar().getBin(), from, to, true);
      if (s != null && s.getZindex() > 0) {
        BagOfCommands.getInstance()
            .addCommand(new DeleteCommand(s, Model.getInstance().getToolBar()));
        return;
      }

    } else if (b == 3) {
      final int x = Math.min(from.x(), to.x());
      final int y = Math.min(from.y(), to.y());
      final int width = Math.abs(to.x() - from.x());
      final int height = Math.abs(to.y() - from.y());
      selection = new Rectangle(x, y, 0, width, height, new Color(255, 0, 0, 255), false);
      Model.getInstance().getCanvas().add(selection);
      ShapeInZoneVisitor zoneVisitor = new ShapeInZoneVisitor(x, y, width, height);
      Model.getInstance().getCanvas().accept(zoneVisitor);
      final List<Shape> zone = zoneVisitor.getResult();
      BagOfCommands.getInstance().addCommand(new GroupCommand(zone));
    }
    Model.getInstance().clearCurrentMenu();
  }
}
