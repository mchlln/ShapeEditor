package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Circle;
import ubx.archilog.model.Group;
import ubx.archilog.model.Rectangle;
import ubx.archilog.model.Shape;
import ubx.archilog.view.editor.*;

public class EditShapeCommand implements Command {

  private Shape shape;
  private ShapeEditorFactory editorFactory;

  public EditShapeCommand(Shape shape) {
    this.shape = shape;
    if (shape instanceof Rectangle) {
      this.editorFactory = new RectangleEditorFactory();
    } else if (shape instanceof Circle) {
      this.editorFactory = new CircleEditorFactory();
    } else if (shape instanceof Group) {
      this.editorFactory = new GroupEditorFactory();
    } else {
      throw new UnsupportedOperationException("Unsupported shape: " + shape);
    }
  }

  @Override
  public void execute() {
    ShapeEditor editor = editorFactory.createEditor();
    editor.edit(shape);
  }

  @Override
  public void undo() {}
}
