package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ChangeColorCommand;
import ubx.archilog.model.Color;
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
      final Shape blueColorButton =
          new ImageRectangle(
              620,
              37,
              1,
              50,
              50,
              "/icons/colors/blue.png",
              () ->
                  BagOfCommands.getInstance()
                      .addCommand(new ChangeColorCommand(shape, new Color(0, 182, 203, 255))));
      final Shape purpleColorButton =
          new ImageRectangle(
              680,
              37,
              1,
              50,
              50,
              "/icons/colors/purple.png",
              () ->
                  BagOfCommands.getInstance()
                      .addCommand(new ChangeColorCommand(shape, new Color(190, 146, 241, 255))));
      final Shape greenColorButton =
          new ImageRectangle(
              740,
              37,
              1,
              50,
              50,
              "/icons/colors/green.png",
              () ->
                  BagOfCommands.getInstance()
                      .addCommand(new ChangeColorCommand(shape, new Color(170, 241, 146, 255))));

      group.add(blueColorButton);
      group.add(purpleColorButton);
      group.add(greenColorButton);
      Model.getInstance().clearCurrentMenu();
      Model.getInstance().setCurrentMenu(group);
    }
  }
}
