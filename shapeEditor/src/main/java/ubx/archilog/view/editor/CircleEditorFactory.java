package ubx.archilog.view.editor;

public class CircleEditorFactory extends ShapeEditorFactory {
  @Override
  public ShapeEditor createEditor() {
    return new CircleEditor();
  }
}
