package ubx.archilog.view.editor;

public class RectangleEditorFactory extends ShapeEditorFactory {
  @Override
  public ShapeEditor createEditor() {
    return new RectangleEditor();
  }
}
