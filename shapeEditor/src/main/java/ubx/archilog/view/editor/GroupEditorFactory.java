package ubx.archilog.view.editor;

public class GroupEditorFactory extends ShapeEditorFactory {
  @Override
  public ShapeEditor createEditor() {
    return new GroupEditor();
  }
}
