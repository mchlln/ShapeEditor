package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Color;
import ubx.archilog.model.Shape;

public class ChangeColorCommand implements Command {

  private final Shape shape;
  private final Color color;

  public ChangeColorCommand(Shape shape, Color color) {
    this.shape = shape;
    this.color = color;
  }

  @Override
  public void execute() {
    shape.setColor(color);
  }

  @Override
  public void undo() {}
}
