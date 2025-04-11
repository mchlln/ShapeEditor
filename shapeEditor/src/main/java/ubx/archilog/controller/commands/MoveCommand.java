package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Position;
import ubx.archilog.model.Shape;

public class MoveCommand implements Command {
  private Shape shape;
  private Position to;
  private Memento shapeMemento;

  public MoveCommand(Shape shape, Position to) {
    this.shape = shape;
    this.shapeMemento = shape.save();
    this.to = to;
  }

  @Override
  public void execute() {
    shape.moveTo(new Position(to.x(), to.y()));
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
