package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ChangeColorCommand;
import ubx.archilog.model.*;
import ubx.archilog.model.shapes.Circle;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;

public class CircleEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof Circle) {
      super.edit(shape, render);
      final Shape colorButton =
          new ImageRectangle(
              620,
              37,
              1,
              50,
              50,
              "/icons/colors.png",
              () -> BagOfCommands.getInstance().addCommand(new ChangeColorCommand(shape, render)));
      group.add(colorButton);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
