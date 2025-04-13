package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.shapes.Polygon;
import ubx.archilog.model.shapes.Shape;

public class AddSideCommand implements Command {

  private final Shape shape;
  private final Memento shapeMemento;

  public AddSideCommand(final Shape shape) {
    this.shape = shape;
    shapeMemento = shape.save();
  }

  @Override
  public void execute() {
    if (shape instanceof Polygon polygon) {
      polygon.sides(polygon.sides() + 1);
    }
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
