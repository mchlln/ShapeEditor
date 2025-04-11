package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Shape;

public class AddToToolBarCommand implements Command {

  Shape shape;
  Shape oldShape;

  public AddToToolBarCommand(final Shape shape) {
    this.shape = shape;
    this.oldShape = shape.clone();
  }

  @Override
  public void execute() {
    shape.setZindex(1);
    Model.getInstance().getToolBar().addShapeToToolBar(shape);
  }

  @Override
  public void undo() {

    Model.getInstance().getToolBar().removeShapeFromToolBar(shape);
  }
}
