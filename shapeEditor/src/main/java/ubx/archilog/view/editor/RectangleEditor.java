package ubx.archilog.view.editor;

import ubx.archilog.model.*;
import ubx.archilog.model.shapes.Rectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.View;
import ubx.archilog.view.editor.templates.ColorEditor;
import ubx.archilog.view.editor.templates.RotationEditor;

public class RectangleEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof Rectangle) {
      super.edit(shape, render);
      final ColorEditor colorEditor = new ColorEditor(group, 520, View.TOP_PADDING);
      colorEditor.edit(shape, render);
      final RotationEditor rotationEditor = new RotationEditor(group, 580, View.TOP_PADDING);
      rotationEditor.edit(shape);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
