package ubx.archilog.controller.commands;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.Command;

public class RedoCommand implements Command {
  @Override
  public void execute() {
    BagOfCommands.getInstance().redoLastCommand();
  }

  @Override
  public void undo() {
    // redo can't be undone
  }
}
