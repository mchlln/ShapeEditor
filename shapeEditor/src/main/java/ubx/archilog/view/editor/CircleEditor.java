package ubx.archilog.view.editor;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.commands.ChangeColorCommand;
import ubx.archilog.model.*;
import ubx.archilog.view.Render;

public class CircleEditor extends AbstractEditor {
  @Override
  public void edit(Shape shape, Render render) {
    if (shape instanceof Circle) {
      super.edit(shape, render);
      Shape blueColorButton =
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
      Shape purpleColorButton =
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
      Shape greenColorButton =
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
