package ubx.archilog.view.editor.templates;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.RotateCommand;
import ubx.archilog.model.shapes.Group;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.Shape;

public class RotationEditor {
  private final Group group;
  private final int x;
  private final int y;

  public RotationEditor(final Group group, final int x, final int y) {
    this.group = group;
    this.x = x;
    this.y = y;
  }

  public void edit(final Shape shape) {
    final Shape rotationButton =
        new ImageRectangle(
            x,
            y,
            1,
            50,
            50,
            "/icons/rotate.png",
            () -> BagOfCommands.getInstance().addCommand(new RotateCommand(shape)));
    group.add(rotationButton);
  }
}
