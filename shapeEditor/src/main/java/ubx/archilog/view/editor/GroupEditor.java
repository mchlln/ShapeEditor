package ubx.archilog.view.editor;

import ubx.archilog.model.Group;
import ubx.archilog.model.Shape;

public class GroupEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape) {
    if (shape instanceof Group) {
      System.out.println("Editing Group");
    }
  }
}
