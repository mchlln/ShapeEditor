package ubx.archilog.view;

import java.util.function.BiFunction;
import java.util.function.Function;
import ubx.archilog.model.Color;
import ubx.archilog.model.Position;

public interface Render {
  void drawRect(int x, int y, int w, int h, boolean fill, Color color);

  void drawImageRect(int x, int y, int w, int h, String path);

  void drawCircle(int x, int y, int radius, Color color);

  void drawPolygon(int[] xCoords, int[] yCoords, int sides, Color color);

  void showTextInputPopUp(String text, Function<String, Void> callBack);

  void showColorPickerPopUp(String text, Color color, Function<Color, Void> callBack);

  void update();

  void initialize(
      int xSize,
      int ySize,
      BiFunction<Position, Integer, Void> mousePressedCallback,
      BiFunction<Position, Integer, Void> mouseReleasedCallback,
      Function<Void, Void> quitCallback);
}
