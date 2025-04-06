package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;

public class AddToToolBarCommand implements Command {

  Shape shape;

  public AddToToolBarCommand(Shape shape) {
    this.shape = shape;
  }

  @Override
  public void execute() {
    shape.setZindex(1);
    Model.getInstance().getToolBar().addShapeToToolBar(shape);
  }

  @Override
  public void undo() {}
}
