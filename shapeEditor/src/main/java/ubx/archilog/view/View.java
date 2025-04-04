package ubx.archilog.view;

import java.util.function.BiFunction;
import javax.swing.*;
import ubx.archilog.model.Color;
import ubx.archilog.model.Position;

public class View {
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;
  private static final int MENU_MARGIN = 32;

  private Position from;

  private Render renderer;

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(WINDOW_WIDTH, WINDOW_HEIGHT, mousePressed, mouseReleased);
    createMenu();
    createToolbar();
  }

  public void createMenu() {
    renderer.drawRect(0, 0, WINDOW_WIDTH, MENU_MARGIN * 2, new Color(189, 142, 231, 255));
  }

  public void createToolbar() {
    renderer.drawRect(0, 0, MENU_MARGIN, WINDOW_HEIGHT, new Color(189, 142, 231, 255));
  }

  public void createCanva() {}

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

    return null;
  }

  public void clickOn(Position position) {
    renderer.drawCircle(position.x(), position.y(), 100, new Color(189, 142, 231, 255));
    System.out.println("Mouse Clicked  " + position);
  }

  public void mouseDragged(Position from, Position to, int b) {
    if (b == 1) {
      renderer.drawRect(
          from.x(), from.y(), to.x() - from.x(), to.y() - from.y(), new Color(189, 142, 231, 255));
    } else if (b == 3) {
      renderer.drawCircle(from.x(), from.y(), to.y() - from.y(), new Color(189, 142, 231, 255));
    }

    System.out.println("Mouse Dragged  " + from + "," + to);
  }
}
