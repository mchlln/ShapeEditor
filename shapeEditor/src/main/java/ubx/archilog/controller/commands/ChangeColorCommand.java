package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.Color;
import ubx.archilog.model.Memento;
import ubx.archilog.model.shapes.AbstractShape;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;

public class ChangeColorCommand implements Command {

  private final Shape shape;
  private final Memento shapeMemento;
  private final Render renderer;
  private boolean firstRun = true;
  private Color selectedColor;

  public ChangeColorCommand(final Shape shape, Render renderer) {
    this.shape = shape;
    this.shapeMemento = shape.save();
    this.renderer = renderer;
  }

  @Override
  public void execute() {
    if (shape instanceof AbstractShape abstractShape) {
      if (firstRun) {
        renderer.showColorPickerPopUp("", abstractShape.getColor(), this::changeColor);
        firstRun = false;
      } else {
        abstractShape.setColor(selectedColor);
      }
    }
  }

  public Void changeColor(final Color color) {
    this.selectedColor = color;
    shape.setColor(color);
    return null;
  }

  @Override
  public void undo() {
    shapeMemento.restore();
  }
}
