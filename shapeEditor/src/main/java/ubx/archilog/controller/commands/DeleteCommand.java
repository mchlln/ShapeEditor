package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Shape;

public class DeleteCommand implements Command {
  private Shape shape;
  Memento modelMemento;

  /*
  public DeleteCommand(Shape shape) {
    this.shape = shape;
    this.modelMemento = Model.getInstance().getComponents().save();
  }

   */
  @Override
  public void execute() {
    // TODO: Find shape and remove it from either canvas or toolbar
  }

  @Override
  public void undo() {
    modelMemento.restore();
  }
}
