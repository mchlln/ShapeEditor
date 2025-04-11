package ubx.archilog.view;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import ubx.archilog.model.Color;
import ubx.archilog.model.Position;

public class AwtRenderer extends Frame implements Render, ActionListener, MouseListener {
  private List<Shape> shapes = new ArrayList<Shape>();
  private BiFunction<Position, Integer, Void> mousePressed;
  private BiFunction<Position, Integer, Void> mouseReleased;
  private boolean init = false;

  @Override
  public void paint(Graphics g) {
    for (final Shape shape : shapes) {
      shape.draw(g);
    }
    System.out.println(shapes.size() + " objects rendered");
    if (init) {
      shapes.clear();
    }
    init = true;
  }

  @Override
  public void drawRect(
      final int x, final int y, final int w, final int h, final boolean fill, final Color color) {
    shapes.add(new Rectangle(x, y, w, h, fill, color));
  }

  @Override
  public void drawImageRect(final int x, final int y, final int w, final int h, final String path) {
    shapes.add(new ImageRectangle(x, y, w, h, path));
  }

  @Override
  public void drawCircle(final int x, final int y, final int radius, final Color color) {
    shapes.add(new Circle(x, y, radius, color));
  }

  @Override
  public void showTextInputPopUp(final String text, final Function<String, Void> callBack) {
    Dialog dialog = new Dialog(this, "Enter Text", true);
    dialog.setLayout(new FlowLayout());

    Label label = new Label(text);
    TextField textField = new TextField(20);
    Button okButton = new Button("OK");

    okButton.addActionListener(
        e -> {
          String input = textField.getText();
          callBack.apply(input);
          dialog.dispose();
        });

    dialog.add(label);
    dialog.add(textField);
    dialog.add(okButton);

    dialog.setSize(300, 120);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
  }

  @Override
  public void update() {
    repaint();
  }

  @Override
  public void initialize(
      final int xSize,
      final int ySize,
      final BiFunction<Position, Integer, Void> mousePressed,
      final BiFunction<Position, Integer, Void> mouseReleased) {
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

    public Rectangle(
        final int x,
        final int y,
        final int width,
        final int height,
        final boolean fill,
        final Color color) {
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

    public Circle(final int x, final int y, final int radius, final Color color) {
      this.x = x;
      this.y = y;
      this.radius = radius;
      this.color = color;
    }

    @Override
    public void draw(Graphics g) {
      g.setColor(new java.awt.Color(color.r(), color.g(), color.b(), color.a()));
      g.fillOval(x, y, radius * 2, radius * 2);
    }
  }

  public static class ImageRectangle extends Canvas implements Shape {
    private final int x;
    private final int y;
    private final int w;
    private final int h;
    Image image;

    public ImageRectangle(
        final int x, final int y, final int width, final int height, final String path) {
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
