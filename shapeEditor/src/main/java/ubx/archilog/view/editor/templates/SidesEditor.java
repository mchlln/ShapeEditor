package ubx.archilog.view.editor.templates;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.AddSideCommand;
import ubx.archilog.controller.commands.RemoveSideCommand;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;
import ubx.archilog.view.View;

public class SidesEditor {
  private final Group group;
  private final int x;
  private final int y;

  public SidesEditor(Group group, int x, int y) {
    this.group = group;
    this.x = x;
    this.y = y;
  }

  public void edit(final Shape shape, final Render render) {
    final Shape addSideButton =
        new ImageRectangle(
            x,
            y,
            View.DEFAULT_Z_INDEX,
            View.DEFAULT_ICON_SIZE,
            View.DEFAULT_ICON_SIZE,
            "/icons/addSide.png",
            () -> BagOfCommands.getInstance().addCommand(new AddSideCommand(shape)));

    final Shape removeSideButton =
        new ImageRectangle(
            x + 60,
            y,
            View.DEFAULT_Z_INDEX,
            View.DEFAULT_ICON_SIZE,
            View.DEFAULT_ICON_SIZE,
            "/icons/removeSide.png",
            () -> BagOfCommands.getInstance().addCommand(new RemoveSideCommand(shape)));
    group.add(addSideButton);
    group.add(removeSideButton);
  }
}
