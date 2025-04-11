package ubx.archilog.view.editor;

import ubx.archilog.model.*;
import ubx.archilog.view.Render;

public class RectangleEditor extends AbstractEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Rectangle) {
      super.edit(shape, render);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
