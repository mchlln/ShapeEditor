package ubx.archilog.controller.commands;

import ubx.archilog.controller.Command;
import ubx.archilog.model.io.XmlLoader;
import ubx.archilog.view.Render;

public class LoadCommand implements Command {

  private final Render renderer;

  public LoadCommand(final Render renderer) {
    this.renderer = renderer;
  }

  @Override
  public void execute() {
    renderer.showTextInputPopUp("Load from: ", this::loadFile);
  }

  private Void loadFile(final String fileName) {
    final XmlLoader loader = new XmlLoader();
    try {
      loader.load(fileName);
    } catch (Exception e) {

    }
    return null;
  }

  @Override
  public void undo() {
    // load can't be undone
  }
}
