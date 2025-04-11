package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Circle;
import ubx.archilog.model.Group;
import ubx.archilog.model.Rectangle;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.editor.*;

public class EditShapeCommand implements Command {

  private Shape shape;
  private ShapeEditorFactory editorFactory;
  private Render render;

  public EditShapeCommand(Shape shape, Render render) {
    this.shape = shape;
    this.render = render;
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
    editor.edit(shape, render);
  }

  @Override
  public void undo() {
    // creation of an editor can't be undone
  }
}
