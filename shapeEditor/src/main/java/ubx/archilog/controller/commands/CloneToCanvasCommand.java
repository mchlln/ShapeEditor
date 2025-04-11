package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.Position;
import ubx.archilog.model.Shape;

public class CloneToCanvasCommand implements Command {
  Shape shape;
  Position position;
  Memento shapeMemento;

  public CloneToCanvasCommand(Shape shape, Position position) {
    this.shape = shape.clone();
    this.position = position;
  }

  @Override
  public void execute() {
    shape.moveTo(new Position(position.x(), position.y()));
    shape.setZindex(1);
    Model.getInstance().getCanvas().add(shape);
  }

  @Override
  public void undo() {
    Model.getInstance().getCanvas().remove(shape);
  }
}
