package ubx.archilog;

import ubx.archilog.controller.BagOfCommands;
import ubx.archilog.model.*;
import ubx.archilog.view.View;

public class Main {
  public static void main(String[] args) {
    final View view = new View();
    Model.getInstance();
    BagOfCommands.getInstance().addObserver(view);
  }
}
