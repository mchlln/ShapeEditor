package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.UngroupCommand;
import ubx.archilog.model.Group;
import ubx.archilog.model.ImageRectangle;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public class GroupEditor implements ShapeEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Group) {
      Shape button =
          new ImageRectangle(
              500,
              37,
              1,
              50,
              50,
              "/icons/redo.png",
              () -> BagOfCommands.getInstance().addCommand(new UngroupCommand((Group) shape)));
      Model.getInstance().getMenu().add(button);
      System.out.println("Editing Group");
    }
  }
}
