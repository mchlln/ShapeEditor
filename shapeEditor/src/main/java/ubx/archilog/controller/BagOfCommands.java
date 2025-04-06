package ubx.archilog.controller;

import java.util.LinkedList;
import java.util.Queue;

public class BagOfCommands {
  private static BagOfCommands instance = null;

  private final Queue<Command> commands = new LinkedList<>();

  private BagOfCommands() {}

  public static BagOfCommands getInstance() {
    if (instance == null) {
      instance = new BagOfCommands();
    }
    return instance;
  }

  public void addCommand(Command command) {
    this.commands.add(command);
    executeAll();
  }

  public void executeOne() {
    if (!commands.isEmpty()) {
      Command command = commands.remove();
      command.execute();
    }
  }

  public void executeAll() {
    while (!commands.isEmpty()) {
      Command command = commands.remove();
      command.execute();
    }
  }
}
