package ubx.archilog.controller.commands;

import java.io.FileWriter;
import java.io.IOException;
import ubx.archilog.controller.Command;
import ubx.archilog.model.Model;
import ubx.archilog.model.io.FileBuilder;
import ubx.archilog.model.io.XmlBuilder;

public class SaveCommand implements Command {
  @Override
  public void execute() {
    FileBuilder builder = Model.getInstance().save(new XmlBuilder());
    if (builder instanceof XmlBuilder) {
      try (FileWriter writer = new FileWriter("save.xml")) {
        writer.write(((XmlBuilder) builder).getResult());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void undo() {
    throw new UnsupportedOperationException("Cannot \"unsave\" a document");
  }
}
