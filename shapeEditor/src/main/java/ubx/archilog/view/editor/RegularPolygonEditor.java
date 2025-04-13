package ubx.archilog.view.editor;

import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.RegularPolygon;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.View;
import ubx.archilog.view.editor.templates.ColorEditor;
import ubx.archilog.view.editor.templates.RotationEditor;
import ubx.archilog.view.editor.templates.SidesEditor;

public class RegularPolygonEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof RegularPolygon) {
      super.edit(shape, render);
      final ColorEditor colorEditor = new ColorEditor(group, 520, View.TOP_PADDING);
      final SidesEditor sidesEditor = new SidesEditor(group, 580, View.TOP_PADDING);
      final RotationEditor rotationEditor = new RotationEditor(group, 700, View.TOP_PADDING);
      colorEditor.edit(shape, render);
      sidesEditor.edit(shape);
      rotationEditor.edit(shape);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
