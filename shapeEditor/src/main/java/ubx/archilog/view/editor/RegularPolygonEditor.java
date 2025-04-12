package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.AddSideCommand;
import ubx.archilog.controller.commands.ChangeColorCommand;
import ubx.archilog.controller.commands.RemoveSideCommand;
import ubx.archilog.model.Model;
import ubx.archilog.model.shapes.ImageRectangle;
import ubx.archilog.model.shapes.RegularPolygon;
import ubx.archilog.model.shapes.Shape;
import ubx.archilog.view.Render;

public class RegularPolygonEditor extends AbstractEditor {
  @Override
  public void edit(final Shape shape, final Render render) {
    if (shape instanceof RegularPolygon) {
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
      final Shape addSideButton =
          new ImageRectangle(
              400,
              37,
              1,
              50,
              50,
              "/icons/addSide.png",
              () -> BagOfCommands.getInstance().addCommand(new AddSideCommand(shape)));

      final Shape removeSideButton =
          new ImageRectangle(
              440,
              37,
              1,
              50,
              50,
              "/icons/removeSide.png",
              () -> BagOfCommands.getInstance().addCommand(new RemoveSideCommand(shape)));

      group.add(colorButton);
      group.add(addSideButton);
      group.add(removeSideButton);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
