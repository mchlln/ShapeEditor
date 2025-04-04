package ubx.archilog.view;

import java.util.function.BiFunction;
import javax.swing.*;
import ubx.archilog.model.Position;

public class View {

  private Position from;

  private Render renderer;

  public View() {
    renderer = new AwtRenderer();
    BiFunction<Position, Integer, Void> mousePressed = this::mousePressed;
    BiFunction<Position, Integer, Void> mouseReleased = this::mouseReleased;
    renderer.initialize(800, 600, mousePressed, mouseReleased);
  }

  public void createMenu() {}

  public void createToolbar() {}

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
    renderer.drawCircle(position.x(), position.y(), 100, "");
    System.out.println("Mouse Clicked  " + position);
  }

  public void mouseDragged(Position from, Position to, int b) {
    if (b == 1) {
      renderer.drawRect(from.x(), from.y(), to.x() - from.x(), to.y() - from.y(), "");
    } else if (b == 3) {
      renderer.drawCircle(from.x(), from.y(), to.y() - from.y(), "");
    }

    System.out.println("Mouse Dragged  " + from + "," + to);
  }
}
