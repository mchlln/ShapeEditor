package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Color;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Shape;

public class ChangeColorCommand implements Command {

  private final Shape shape;
  private final Color color;
  private final Memento shapeMemento;

  public ChangeColorCommand(Shape shape, Color color) {
    this.shape = shape;
    this.color = color;
    this.shapeMemento = shape.save();
  }

  @Override
  public void execute() {
    shape.setColor(color);
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
