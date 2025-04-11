package ubx.archilog.controller.commands;

import java.io.FileWriter;
import java.io.IOException;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Model;
import ubx.archilog.model.io.FileBuilder;
import ubx.archilog.model.io.XmlBuilder;
import ubx.archilog.view.Render;

public class SaveCommand implements Command {

  private final Render renderer;

  public SaveCommand(Render renderer) {
    this.renderer = renderer;
  }

  @Override
  public void execute() {
    renderer.showTextInputPopUp("Save to: ", this::saveFile);
  }

  private Void saveFile(String fileName) {
    FileBuilder builder = Model.getInstance().save(new XmlBuilder());
    if (builder instanceof XmlBuilder) {
      try (FileWriter writer = new FileWriter(fileName)) {
        writer.write(((XmlBuilder) builder).getResult());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public void undo() {
    // save can't be undone
  }
}
