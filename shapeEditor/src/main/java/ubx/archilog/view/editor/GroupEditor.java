package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.UngroupCommand;
import ubx.archilog.model.Group;
import ubx.archilog.model.ImageRectangle;
import ubx.archilog.model.Model;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public class GroupEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof Group) {
      super.edit(shape, render);
      final Shape ungroupButton =
          new ImageRectangle(
              440,
              37,
              1,
              50,
              50,
              "/icons/ungroup.png",
              () -> BagOfCommands.getInstance().addCommand(new UngroupCommand((Group) shape)));

      group.add(ungroupButton);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
