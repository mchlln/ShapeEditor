package ubx.archilog.view.editor;

import ubx.archilog.model.Rectangle;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public class RectangleEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Rectangle) {
      System.out.println("Editing Rectangle");
    }
  }
}
