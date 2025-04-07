package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Shape;

public class ResizeCommand implements Command {

  private float scale;
  private Shape shape;

  public ResizeCommand(Shape shape, float scale) {
    this.shape = shape;
    this.scale = scale;
  }

  @Override
  public void execute() {
    shape.scale(this.scale);
  }

  @Override
  public void undo() {}
}
