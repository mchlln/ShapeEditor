package ubx.archilog.controller.commands;

import java.util.List;
import ubx.archilog.controller.Command;
import ubx.archilog.model.shapes.Memento;
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
    final List<Shape> shapes = List.copyOf(group.shapes());
    System.out.println("To ungroup: " + shapes);
    for (final Shape shape : shapes) {
      System.out.println("Ungrouping shape: " + shape);
      if (shape instanceof Group) {
        shape.zIndex(1);
        ((Group) shape).updateChildZIndex();

      } else {
        shape.zIndex(1);
      }
      group.remove(shape);
      Model.getInstance().getCanvas().add(shape);
      System.out.println("Remaining: " + group.shapes() + " + in shape " + shapes);
    }
  }

  @Override
  public void undo() {
    System.out.println("undo ungroup command");
    groupMemento.restore();
    System.out.println("Ungroup restored : " + group.shapes());
  }
}
