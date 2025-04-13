package ubx.archilog.controller.commands;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
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
      newGroup = new Group();
      for (final Shape shape : shapes) {
        Model.getInstance().getCanvas().getShapes().remove(shape); // Unlink from canvas
        shapesMemento.add(shape.save());
        newGroup.add(shape);
      }
      newGroup.updateChildZIndex();
      Model.getInstance().getCanvas().add(newGroup);
    }
  }

  @Override
  public void undo() { // TODO: Don't do a group if size < 1
    Model.getInstance().getCanvas().remove(newGroup);
    for (final Memento memento : shapesMemento) {
      memento.restore();
    }
    for (final Shape shape : newGroup.getShapes()) {
      Model.getInstance().getCanvas().add(shape);
    }
  }
}
