package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.shapes.Shape;

public class RotateCommand implements Command {

  private final Shape shape;
  private final Memento memento;

  public RotateCommand(Shape shape) {
    this.shape = shape;
    this.memento = shape.save();
  }

  @Override
  public void execute() {
    shape.rotate();
  }

  @Override
  public void undo() {
    memento.restore();
  }
}
