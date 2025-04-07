package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ResizeCommand;
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
      Shape ungroupButton =
          new ImageRectangle(
              500,
              37,
              1,
              50,
              50,
              "/icons/redo.png",
              () -> BagOfCommands.getInstance().addCommand(new UngroupCommand((Group) shape)));
      Shape scaleButton =
          new ImageRectangle(
              600,
              37,
              1,
              50,
              50,
              "/icons/bin.png",
              () -> BagOfCommands.getInstance().addCommand(new ResizeCommand((Group) shape, 2f)));
      // Model.getInstance().getMenu().add(button);
      Group group = new Group();
      group.setZindex(0);
      group.add(ungroupButton);
      group.add(scaleButton);
      Model.getInstance().setCurrentMenu(ungroupButton);
      System.out.println("Editing Group");
    }
  }
}
