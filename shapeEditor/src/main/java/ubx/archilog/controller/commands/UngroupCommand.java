package ubx.archilog.controller.commands;

import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Memento;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.Shape;

public class UngroupCommand implements Command {
  private final Group group;
  private final Memento groupMemento;

  public UngroupCommand(final Group group) {
    this.group = group;
    this.groupMemento = group.save();
  }

  @Override
  public void execute() {
    final List<Shape> shapes = List.copyOf(group.getShapes());
    System.out.println("To ungroup: " + shapes);
    for (final Shape shape : shapes) {
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
  public void undo() {
    System.out.println("undo ungroup command");
    groupMemento.restore();
    System.out.println("Ungroup restored : " + group.getShapes());
  }
}
