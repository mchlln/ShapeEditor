package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.Position;
import ubx.archilog.model.shapes.Shape;

public class MoveCommand implements Command {
  private final Shape shape;
  private final Position to;
  private final Memento shapeMemento;

  public MoveCommand(final Shape shape, final Position to) {
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
