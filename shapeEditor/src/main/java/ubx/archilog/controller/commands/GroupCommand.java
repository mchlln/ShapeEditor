package ubx.archilog.controller.commands;

import java.util.ArrayList;
import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Group;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;

public class GroupCommand implements Command {

  List<Shape> shapes;
  private Group newGroup;
  private List<Memento> shapesMemento = new ArrayList<>();

  public GroupCommand(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void execute() {
    if (shapes.size() > 1) {
      newGroup = new Group();
      for (Shape s : shapes) {
        shapesMemento.add(s.save());
        newGroup.add(s);
      }
      newGroup.updateChildZIndex();
      Model.getInstance().getCanvas().add(newGroup);
    }
  }

  @Override
  public void undo() {
    Model.getInstance().getCanvas().remove(newGroup);
    for (Memento m : shapesMemento) {
      m.restore();
    }
    for (Shape s : newGroup.getShapes()) {
      Model.getInstance().getCanvas().add(s);
    }
  }
}
