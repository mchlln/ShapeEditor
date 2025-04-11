package ubx.archilog.controller;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import ubx.archilog.controller.commands.*;

public class BagOfCommands {
  private static BagOfCommands instance = null;

  private final Queue<Command> commands = new LinkedList<>();

  private final Stack<Command> undoCommands = new Stack<>();
  private final Stack<Command> redoCommands = new Stack<>();

  private BagOfCommands() {}

  public static BagOfCommands getInstance() {
    if (instance == null) {
      instance = new BagOfCommands();
    }
    return instance;
  }

  public void addCommand(final Command command) {
    this.commands.add(command);
    executeAll();
  }

  public void executeOne() {
    if (!commands.isEmpty()) {
      final Command command = commands.remove();
      if (!(command instanceof UndoCommand)
          && !(command instanceof RedoCommand)
          && !(command instanceof EditShapeCommand)
          && !(command instanceof LoadCommand)
          && !(command instanceof SaveCommand)) {
        undoCommands.push(command);
        System.out.println("undo command pushed: " + undoCommands.peek());
        redoCommands.clear();
      }
      command.execute();
    }
  }

  public void executeAll() {
    while (!commands.isEmpty()) {
      final Command command = commands.remove();
      if (!(command instanceof UndoCommand)
          && !(command instanceof RedoCommand)
          && !(command instanceof EditShapeCommand)
          && !(command instanceof LoadCommand)
          && !(command instanceof SaveCommand)) {
        undoCommands.push(command);
        System.out.println("undo command pushed: " + undoCommands.peek());
        redoCommands.clear();
      }
      command.execute();
    }
  }

  public void undoLastCommand() {
    if (!undoCommands.isEmpty()) {
      final Command command = undoCommands.pop();
      System.out.println("undo command popped: " + command);
      redoCommands.push(command);
      System.out.println("redo command pushed: " + redoCommands.peek());
      command.undo();
    }
  }

  public void redoLastCommand() {
    if (!redoCommands.isEmpty()) {
      final Command command = redoCommands.pop();
      System.out.println("redo command popped: " + command);
      undoCommands.push(command);
      System.out.println("undo command pushed: " + undoCommands.peek());
      command.execute();
    }
  }
}
