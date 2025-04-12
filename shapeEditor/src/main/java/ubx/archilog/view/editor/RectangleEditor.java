package ubx.archilog.view.editor;

import ubx.archilog.model.*;
import ubx.archilog.model.shapes.Rectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.View;
import ubx.archilog.view.editor.templates.ColorEditor;

public class RectangleEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof Rectangle) {
      super.edit(shape, render);
      ColorEditor colorEditor = new ColorEditor(group, 520, View.TOP_PADDING);
      colorEditor.edit(shape, render);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
