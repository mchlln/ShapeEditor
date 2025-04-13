package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.shapes.Shape;

public class TranslateCommand implements Command {
  private final Shape shape;
  private final int diffX, diffY;
  private final Memento shapeMemento;

  public TranslateCommand(final Shape shape, final int diffX, final int diffY) {
    this.shape = shape;
    this.shapeMemento = shape.save();
    this.diffX = diffX;
    this.diffY = diffY;
  }

  @Override
  public void execute() {
    shape.translate(diffX, diffY);
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
