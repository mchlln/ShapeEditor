package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Polygon;
import ubx.archilog.model.shapes.Shape;

public class RemoveSideCommand implements Command {

  Shape shape;

  public RemoveSideCommand(Shape shape) {
    this.shape = shape;
  }

  @Override
  public void execute() {
    if (shape instanceof Polygon polygon && polygon.getSides() > 3) {
      polygon.setSides(polygon.getSides() - 1);
    }
  }

  @Override
  public void undo() {}
}
