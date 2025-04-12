package ubx.archilog.view.editor.templates;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ChangeColorCommand;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;

public class ColorEditor {
  private final Group group;
  private final int x;
  private final int y;

  public ColorEditor(Group group, int x, int y) {
    this.group = group;
    this.x = x;
    this.y = y;
  }

  public void edit(final Shape shape, final Render render) {
    final Shape colorButton =
        new ImageRectangle(
            x,
            y,
            1,
            50,
            50,
            "/icons/colors.png",
            () -> BagOfCommands.getInstance().addCommand(new ChangeColorCommand(shape, render)));
    group.add(colorButton);
  }
}
