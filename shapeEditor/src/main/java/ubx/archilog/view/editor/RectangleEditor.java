package ubx.archilog.view.editor;

import ubx.archilog.model.Rectangle;
import ubx.archilog.model.Shape;

public class RectangleEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape) {
    if (shape instanceof Rectangle) {
      System.out.println("Editing Circle");
    }
  }
}
