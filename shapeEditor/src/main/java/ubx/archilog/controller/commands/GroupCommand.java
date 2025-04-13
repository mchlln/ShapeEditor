package ubx.archilog.controller.commands;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.Shape;

public class GroupCommand implements Command {

  private final List<Shape> shapes;
  private Group newGroup;
  private final List<Memento> shapesMemento = new ArrayList<>();

  public GroupCommand(final List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void execute() {
    if (shapes.size() > 1) {
      newGroup = new Group(false);
      for (final Shape shape : shapes) {
        Model.getInstance().getCanvas().remove(shape); // Unlink from canvas
        shapesMemento.add(shape.save());
        newGroup.add(shape);
      }
      newGroup.updateChildZIndex();
      Model.getInstance().getCanvas().add(newGroup);
    }
  }

  @Override
  public void undo() {
    if (shapes.size() < 2) return;
    Model.getInstance().getCanvas().remove(newGroup);
    for (final Memento memento : shapesMemento) {
      memento.restore();
    }
    for (final Shape shape : shapes) {
      Model.getInstance().getCanvas().add(shape);
    }
  }
}
