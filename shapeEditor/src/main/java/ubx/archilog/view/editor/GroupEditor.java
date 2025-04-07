package ubx.archilog.view.editor;

import ubx.archilog.model.Group;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public class GroupEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Group) {
      System.out.println("Editing Group");
    }
  }
}
