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
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int MENU_MARGIN = 50;

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

  public Void mouseReleased(final Position position, final int button) {
    if (from.equals(position)) {
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

  public void mouseDragged(final Position from, final Position to, final int b) {
    Model.getInstance().getCanvas().remove(selection);
    if (b == 1) {
      final IsInVisitor fromAppSector = new IsInVisitor(from.x(), from.y());
      final IsInVisitor toAppSector = new IsInVisitor(to.x(), to.y());
      final Model model = Model.getInstance();

      // Case adding shape to canvas
      model.getToolBar().accept(fromAppSector);
      model.getCanvas().accept(toAppSector);
      List<Shape> in = fromAppSector.getResult();
      List<Shape> out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        final Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new CloneToCanvasCommand(toAdd, to));
        }
      }

      // Case adding shape to toolbar
      model.getCanvas().accept(fromAppSector);
      model.getToolBar().accept(toAppSector);
      in = fromAppSector.getResult();
      out = toAppSector.getResult();

      // Special case for delete from canvas
      model.getToolBar().getBin().accept(toAppSector);
      final List<Shape> outBin = toAppSector.getResult();
      if (!in.isEmpty() && !outBin.isEmpty()) {
        final Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance()
              .addCommand(new DeleteCommand(toAdd, Model.getInstance().getCanvas()));
          return;
        }
      }

      if (!in.isEmpty() && !out.isEmpty()) {
        final Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance().addCommand(new AddToToolBarCommand(toAdd));
        }
      }

      // Moving shape from canvas
      model.getCanvas().accept(toAppSector);
      out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        final Shape toMove = model.getBestZIndex(in);
        if (toMove.getZindex() > 0) {
          final int deltaX = to.x() - from.x();
          final int deltaY = to.y() - from.y();
          BagOfCommands.getInstance().addCommand(new TranslateCommand(toMove, deltaX, deltaY));
        }
      }

      model.getToolBar().accept(fromAppSector);
      model.getToolBar().getBin().accept(toAppSector);
      in = fromAppSector.getResult();
      out = toAppSector.getResult();
      if (!in.isEmpty() && !out.isEmpty()) {
        final Shape toAdd = model.getBestZIndex(in).clone();
        if (toAdd.getZindex() > 0) {
          BagOfCommands.getInstance()
              .addCommand(new DeleteCommand(toAdd, Model.getInstance().getToolBar()));
          return;
        }
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
