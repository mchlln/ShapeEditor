package ubx.archilog.controller.commands;

import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Group;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;

public class UngroupCommand implements Command {
  private Group group;

  public UngroupCommand(Group group) {
    this.group = group;
  }

  @Override
  public void execute() {
    List<Shape> shapes = List.copyOf(group.getShapes());
    System.out.println("To ungroup: " + shapes);
    for (Shape shape : shapes) {
      System.out.println("Ungrouping shape: " + shape);
      if (shape instanceof Group) {
        shape.setZindex(1);
        ((Group) shape).updateChildZIndex();

      } else {
        shape.setZindex(1);
      }
      group.remove(shape);
      Model.getInstance().getCanvas().add(shape);
      System.out.println("Remaining: " + group.getShapes() + " + in shape " + shapes);
    }
  }

  @Override
  public void undo() {}
}
