package ubx.archilog.view.editor;

public class RegularPolygonEditorFactory extends ShapeEditorFactory {
  @Override
  public ShapeEditor createEditor() {
    return new RegularPolygonEditor();
  }
}
