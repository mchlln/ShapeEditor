package ubx.archilog.view.editor;

import ubx.archilog.model.Circle;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public class CircleEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Circle) {
      System.out.println("Editing Circle");
    }
  }
}
