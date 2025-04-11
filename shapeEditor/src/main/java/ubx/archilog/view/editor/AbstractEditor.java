package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ResizeCommand;
import ubx.archilog.model.Group;
import ubx.archilog.model.ImageRectangle;
import ubx.archilog.model.Shape;
import ubx.archilog.view.Render;

public abstract class AbstractEditor implements ShapeEditor {

  protected Group group;

  @Override
  public void edit(Shape shape, Render render) {
    Shape scaleIncreaseButton =
        new ImageRectangle(
            500,
            37,
            1,
            50,
            50,
            "/icons/plus.png",
            () -> BagOfCommands.getInstance().addCommand(new ResizeCommand(shape, 1.2f)));
    Shape scaleDecreaseButton =
        new ImageRectangle(
            560,
            37,
            1,
            50,
            50,
            "/icons/minus.png",
            () -> BagOfCommands.getInstance().addCommand(new ResizeCommand(shape, 0.8f)));

    group = new Group();
    group.setZindex(0);
    group.add(scaleIncreaseButton);
    group.add(scaleDecreaseButton);
  }
}
