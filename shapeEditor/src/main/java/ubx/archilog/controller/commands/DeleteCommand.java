package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.model.shapes.ToolBar;

public class DeleteCommand implements Command {
  private final Shape shape;
  private final Group from;
  private final Memento modelMemento;

  public DeleteCommand(final Shape shape, final Group from) {
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
  }

  @Override
  public void undo() {
    modelMemento.restore();
  }
}
