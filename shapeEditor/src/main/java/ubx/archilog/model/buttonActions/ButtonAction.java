package ubx.archilog.model.buttonActions;

import ubx.archilog.model.Shape;

public interface ButtonAction {

  void onDrag(Shape shape);

  void onClick();
}
