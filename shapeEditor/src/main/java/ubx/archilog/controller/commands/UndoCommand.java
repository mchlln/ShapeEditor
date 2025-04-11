package ubx.archilog.controller.commands;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.controller.Command;

public class UndoCommand implements Command {
  @Override
  public void execute() {
    BagOfCommands.getInstance().undoLastCommand();
  }

  @Override
  public void undo() {}
}
