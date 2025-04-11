package ubx.archilog.controller.commands;

import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Group;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;

public class GroupCommand implements Command {

  List<Shape> shapes;

  public GroupCommand(List<Shape> shapes) {
    this.shapes = shapes;
  }

  @Override
  public void execute() {
    if (shapes.size() > 1) {
      Group newGroup = new Group();
      for (Shape s : shapes) {
        newGroup.add(s);
      }
      newGroup.updateChildZIndex();
      Model.getInstance().getCanvas().add(newGroup);
    }
  }

  @Override
  public void undo() {}
}
