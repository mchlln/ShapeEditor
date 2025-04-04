package ubx.archilog.view;

import java.util.function.BiFunction;
import ubx.archilog.model.Color;
import ubx.archilog.model.Position;

public interface Render {
  void drawRect(int x, int y, int w, int h, boolean fill, Color color);

  void drawImageRect(int x, int y, int w, int h, String path);

  void drawCircle(int x, int y, int radius, Color color);

  void update();

  void initialize(
      int xSize,
      int ySize,
      BiFunction<Position, Integer, Void> mousePressed,
      BiFunction<Position, Integer, Void> mouseReleased);
}
