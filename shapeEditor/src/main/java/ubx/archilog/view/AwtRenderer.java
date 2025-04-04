package ubx.archilog.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.model.Position;

public class AwtRenderer extends Frame implements Render, ActionListener, MouseListener {
  private List<Shape> shapes = new ArrayList<Shape>();
  BiFunction<Position, Integer, Void> mousePressed;
  BiFunction<Position, Integer, Void> mouseReleased;

  @Override
  public void paint(Graphics g) {
    for (Shape shape : shapes) {
      shape.draw(g);
    }
  }

  @Override
  public void drawRect(int x, int y, int w, int h, String color) {
    shapes.add(new Rectangle(x, y, w, h, "#00000"));
    repaint();
  }

  @Override
  public void drawImageRect(int x, int y, int w, int h, String path) {}

  @Override
  public void drawCircle(int x, int y, int radius, String color) {
    shapes.add(new Circle(x, y, radius, "#00000"));
    repaint();
  }

  @Override
  public void onClick(int x, int y) {}

  @Override
  public void initialize(
      int xSize,
      int ySize,
      BiFunction<Position, Integer, Void> mousePressed,
      BiFunction<Position, Integer, Void> mouseReleased) {
    setLayout(new FlowLayout());
    setTitle("Basic AWT App");

    this.mousePressed = mousePressed;
    this.mouseReleased = mouseReleased;

    setSize(xSize, ySize);
    setVisible(true);

    addMouseListener(this);

    addWindowListener(
        new WindowAdapter() {
          public void windowClosing(WindowEvent e) {
            dispose();
          }
        });
  }

  @Override
  public void actionPerformed(ActionEvent actionEvent) {}

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {}

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    mousePressed.apply(new Position(mouseEvent.getX(), mouseEvent.getY()), mouseEvent.getButton());
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    mouseReleased.apply(new Position(mouseEvent.getX(), mouseEvent.getY()), mouseEvent.getButton());
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {}

  @Override
  public void mouseExited(MouseEvent mouseEvent) {}

  public interface Shape {
    void draw(Graphics g);
  }

  public static class Rectangle implements Shape {
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public Rectangle(int x, int y, int width, int height, String color) {
      this.x = x;
      this.y = y;
      this.w = width;
      this.h = height;
    }

    @Override
    public void draw(Graphics g) {
      g.fillRect(x, y, w, h);
    }
  }

  public static class Circle implements Shape {
    private final int x;
    private final int y;
    private final int radius;

    public Circle(int x, int y, int radius, String color) {
      this.x = x;
      this.y = y;
      this.radius = radius;
    }

    @Override
    public void draw(Graphics g) {
      g.fillOval(x, y, radius, radius);
    }
  }
}
