package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.model.shapes.ToolBar;

public class DeleteCommand implements Command {
  private Shape shape;
  private Group from;
  Memento modelMemento;

  public DeleteCommand(Shape shape, Group from) {
    this.shape = shape;
    this.from = from;
    this.modelMemento = Model.getInstance().getComponents().save();
  }

  @Override
  public void execute() {
    if (from instanceof ToolBar toolBar) {
      toolBar.removeShapeFromToolBar(shape);
    } else {
      from.remove(shape);
    }
    // TODO: Find shape and remove it from either canvas or toolbar
  }

  @Override
  public void undo() {
    modelMemento.restore();
  }
}
