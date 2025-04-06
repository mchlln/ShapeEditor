package ubx.archilog.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import ubx.archilog.model.Color;
import ubx.archilog.model.Position;

public class AwtRenderer extends Frame implements Render, ActionListener, MouseListener {
  private List<Shape> shapes = new ArrayList<Shape>();
  private BiFunction<Position, Integer, Void> mousePressed;
  private BiFunction<Position, Integer, Void> mouseReleased;
  private boolean init = false;

  @Override
  public void paint(Graphics g) {
    for (Shape shape : shapes) {
      shape.draw(g);
    }
    System.out.println(shapes.size() + " objects rendered");
    if (init) {
      shapes.clear();
    }
    init = true;
  }

  @Override
  public void drawRect(int x, int y, int w, int h, boolean fill, Color color) {
    shapes.add(new Rectangle(x, y, w, h, fill, color));
    // EventQueue.invokeLater(this::updateShapes);
  }

  @Override
  public void drawImageRect(int x, int y, int w, int h, String path) {
    shapes.add(new ImageRectangle(x, y, w, h, path));
    // EventQueue.invokeLater(this::updateShapes);
  }

  @Override
  public void drawCircle(int x, int y, int radius, Color color) {
    shapes.add(new Circle(x, y, radius, color));
    // EventQueue.invokeLater(this::updateShapes);
  }

  @Override
  public void update() {
    repaint();
  }

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
    private final boolean fill;
    private final Color color;

    public Rectangle(int x, int y, int width, int height, boolean fill, Color color) {
      this.x = x;
      this.y = y;
      this.w = width;
      this.h = height;
      this.fill = fill;
      this.color = color;
    }

    @Override
    public void draw(Graphics g) {
      g.setColor(new java.awt.Color(color.r(), color.g(), color.b(), color.a()));
      if (fill) {
        g.fillRect(x, y, w, h);
      } else {
        g.drawRect(x, y, w, h);
      }
    }

    @Override
    public String toString() {
      return "Rectangle(" + x + "," + y + ") + color=" + color;
    }
  }

  public static class Circle implements Shape {
    private final int x;
    private final int y;
    private final int radius;
    private final Color color;

    public Circle(int x, int y, int radius, Color color) {
      this.x = x;
      this.y = y;
      this.radius = radius;
      this.color = color;
    }

    @Override
    public void draw(Graphics g) {
      g.setColor(new java.awt.Color(color.r(), color.g(), color.b(), color.a()));
      g.fillOval(x, y, radius, radius);
    }
  }

  public static class ImageRectangle extends Canvas implements Shape {
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    Image image;

    public ImageRectangle(int x, int y, int width, int height, String path) {
      this.x = x;
      this.y = y;
      this.w = width;
      this.h = height;

      try {
        image = Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
      } catch (Exception e) {
        System.out.println("Error loading image: " + e.getMessage());
      }
    }

    @Override
    public void draw(Graphics g) {
      if (image != null) {
        g.drawImage(image, x, y, w, h, this);
      } else {
        g.drawRect(x, y, w, h);
      }
    }
  }
}
