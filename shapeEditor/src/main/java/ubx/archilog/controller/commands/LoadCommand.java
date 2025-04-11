package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.io.XmlLoader;
import ubx.archilog.view.Render;

public class LoadCommand implements Command {

  private final Render renderer;

  public LoadCommand(Render renderer) {
    this.renderer = renderer;
  }

  @Override
  public void execute() {
    renderer.showTextInputPopUp("Save to: ", this::loadFile);
  }

  private Void loadFile(String fileName) {
    XmlLoader a = new XmlLoader();
    try {
      a.load(fileName);
    } catch (Exception e) {

    }
    return null;
  }

  @Override
  public void undo() {}
}
