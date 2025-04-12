package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.UngroupCommand;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.View;

public class GroupEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof Group) {
      super.edit(shape, render);
      final Shape ungroupButton =
          new ImageRectangle(
              520,
              View.TOP_PADDING,
              View.DEFAULT_Z_INDEX,
              View.DEFAULT_ICON_SIZE,
              View.DEFAULT_ICON_SIZE,
              "/icons/ungroup.png",
              () -> BagOfCommands.getInstance().addCommand(new UngroupCommand((Group) shape)));

      group.add(ungroupButton);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
