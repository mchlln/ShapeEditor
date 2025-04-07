package ubx.archilog.view.editor;

import ubx.archilog.model.Circle;
import ubx.archilog.model.Shape;

public class CircleEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape) {
    if (shape instanceof Circle) {
      System.out.println("Editing Circle");
    }
  }
}
