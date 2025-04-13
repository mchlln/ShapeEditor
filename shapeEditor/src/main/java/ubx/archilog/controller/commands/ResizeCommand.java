package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.shapes.Shape;

public class ResizeCommand implements Command {

  private final float scale;
  private final Shape shape;
  private final Memento shapeMemento;

  public ResizeCommand(final Shape shape, final float scale) {
    this.shape = shape;
    this.scale = scale;
    this.shapeMemento = shape.save();
  }

  @Override
  public void execute() {
    shape.scale(this.scale);
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
